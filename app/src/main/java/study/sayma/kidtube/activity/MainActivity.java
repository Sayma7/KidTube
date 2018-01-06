package study.sayma.kidtube.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import study.sayma.kidtube.R;
import study.sayma.kidtube.models.VideoItem;
import study.sayma.kidtube.utils.U;

public class MainActivity extends YouTubeBaseActivity implements EasyPermissions.PermissionCallbacks,
        YouTubePlayer.OnInitializedListener, View.OnClickListener {

    public static final String API_KEY = "AIzaSyA0Ob1g4gfL7bSi6ui2j0v5oJU5QqWcGZY";
    public static final String PlayList_ID = "PLdkj6XH8GYPQZcdkgZ7t-yKC5j09QhdqZ";
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SEEK_UPDATE_INTERVAL_IN_MILLIS = 250;
    private static final String BUTTON_TEXT = "Call YouTube Data API";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {YouTubeScopes.YOUTUBE_READONLY};
    private static YouTubePlayer player;
    GoogleAccountCredential mCredential;
    ProgressDialog mProgress;
    private YouTubePlayerView playerView;
    private boolean isFullScreen;
    private Handler seekHandler;
    private SeekBar seekBar;
    private TextView tvCur, tvLast;
    private boolean isActivityVisible;
    private RelativeLayout rlControlHolder;
    private boolean isClassLocked;
    private int totalLength;
    private TextView mOutputText;
    private Button mCallApiButton;

    private Runnable seekRunner = new Runnable() {
        @Override
        public void run() {
            if (player != null) {
                int ct = player.getCurrentTimeMillis();
                tvCur.setText(U.getSmallFormattedTime(ct / 1000));
                int p = (ct * 100) / (totalLength < 1 ? 1 : totalLength);
                seekBar.setProgress(p);
                runSeekChanger();
            } else {
                Log.e(TAG, "Seek-Runner : YoutubePlayer object is NULL !!!");
            }
        }
    };

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            ImageView btn = findViewById(R.id.btnPlay);
            if (player != null && !player.isPlaying())
                playVideo(btn);
            else if (player != null && player.isPlaying())
                btn.setBackgroundResource(R.drawable.btn_pause);
        }

        @Override
        public void onPaused() {
            if (player != null && player.getCurrentTimeMillis() > 3000)
                showControls(true);
            ImageView btn = findViewById(R.id.btnPlay);
            if (player != null && player.isPlaying())
                pauseVideo(btn);
            else
                btn.setBackgroundResource(R.drawable.ic_play_circle_outline_red_400_24dp);
        }

        @Override
        public void onStopped() {
            if (player != null && player.getCurrentTimeMillis() > 3000)
                showControls(true);
            ImageView btn = findViewById(R.id.btnPlay);
            if (player != null && player.isPlaying()) {
                showControls(true);
                pauseVideo(btn);
            } else
                btn.setBackgroundResource(R.drawable.ic_play_circle_outline_red_400_24dp);
        }

        @Override
        public void onBuffering(boolean isBuffering) {
            ViewGroup ytView = playerView; // if you are using YouTubePlayerView
            ProgressBar progressBar;
            try {
                // As of 2016-02-16, the ProgressBar is at position 0 -> 3 -> 2
                // in the view tree of the Youtube Player Fragment/View
                ViewGroup child1 = (ViewGroup) ytView.getChildAt(0);
                ViewGroup child2 = (ViewGroup) child1.getChildAt(3);
                progressBar = (ProgressBar) child2.getChildAt(2);
            } catch (Throwable t) {
                // As its position may change, we fallback to looking for it
                progressBar = findProgressBar(ytView);
                // I recommend reporting this problem so that you can update the code
                // in the try branch: direct access is more efficient than searching for it
            }

            int visibility = isBuffering ? View.VISIBLE : View.INVISIBLE;
            if (progressBar != null) {
                progressBar.setVisibility(visibility);
                // Note that you could store the ProgressBar instance somewhere from here,
                // and use that later instead of accessing it again.
            }
        }

        @Override
        public void onSeekTo(int i) {
            seekHandler.postDelayed(seekRunner, 100);
        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
            showControls(false);
        }

        @Override
        public void onLoaded(String s) {
            Log.d(TAG, "Youtube video onLoaded : " + s);
            showControls(true);
        }


        @Override
        public void onAdStarted() {
        }

        @Override
        public void onVideoStarted() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            Log.e(TAG, "Error occurred: " + errorReason.name());
            if (errorReason != YouTubePlayer.ErrorReason.UNAUTHORIZED_OVERLAY
                    && errorReason != YouTubePlayer.ErrorReason.PLAYER_VIEW_NOT_VISIBLE)
                showControls(false);
        }
    };
    private VideoItem video;

    private void runSeekChanger() {
        if (seekHandler == null)
            seekHandler = new Handler();
        seekHandler.postDelayed(seekRunner, SEEK_UPDATE_INTERVAL_IN_MILLIS);
    }

    private void pauseVideo() {
        ImageView btn = findViewById(R.id.btnPlay);
        if (btn != null)
            pauseVideo(btn);
    }

    private void playVideo() {
        ImageView btn = findViewById(R.id.btnPlay);
        if (btn != null)
            playVideo(btn);
    }

    private void pauseVideo(ImageView btn) {
        player.pause();
        btn.setBackgroundResource(R.drawable.ic_play_circle_outline_red_400_24dp);
    }

    private void playVideo(ImageView btn) {
        player.play();
        btn.setBackgroundResource(R.drawable.btn_pause);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                if (null != player && !player.isPlaying())
                    player.play();
                break;
            case R.drawable.btn_pause:
                if (null != player && player.isPlaying())
                    player.pause();
                break;
        }
    }

    @Override
    protected void onStop() {
        player = null;
        if (seekHandler != null)
            seekHandler.removeCallbacks(seekRunner);
        seekHandler = null;
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
    }

    @Override
    protected void onPause() {
        if (player != null && player.isPlaying())
            pauseVideo();
        super.onPause();
        isActivityVisible = false;
    }

    @Override
    protected void onDestroy() {
        if (player != null)
            player.release();
        player = null;
        if (seekHandler != null)
            seekHandler.removeCallbacks(seekRunner);
        seekHandler = null;
        super.onDestroy();
    }

    private void showControls(boolean isToShow) {
        if (rlControlHolder == null)
            rlControlHolder = findViewById(R.id.rl_control_holder);
        if (!isToShow) {
            if (rlControlHolder.getVisibility() == View.VISIBLE)
                // U.collapse(rlControlHolder);
                rlControlHolder.setVisibility(View.GONE);
            return;
        }
        if (rlControlHolder.getVisibility() != View.VISIBLE)
            // U.expand(rlControlHolder);
            rlControlHolder.setVisibility(View.VISIBLE);

        final ImageView btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player == null)
                    return;
                if (player.isPlaying()) {
                    pauseVideo(btnPlay);
                } else player.play();
            }
        });/*
        ImageView btnNext = findViewById(R.id.btnNextPlayer);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                player.seekRelativeMillis(-10 * 1000);
            }
        });
        ImageView btnPrevious = findViewById(R.id.btnPreviousPlayer);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                player.seekRelativeMillis(10 * 1000);
            }
        });
        ImageView btnReplay = findViewById(R.id.btnReplayPlayer);
        btnReplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                player.seekToMillis(0);
            }
        });*/
        ImageView btnFullScreen = findViewById(R.id.btnFullScreenPlayer);
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (U.isDevicePortrait(getResources()))
                    setFullscreen(true);
                else
                    setFullscreen(false);
            }
        });
        configureSeekBar();
    }

    private void configureSeekBar() {
        if (player == null)
            return;
        tvCur.setText(U.getSmallFormattedTime(0));
        totalLength = player.getDurationMillis();
        tvLast.setText(U.getSmallFormattedTime(totalLength / 1000));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    final int c = progress * totalLength / 100;
                    Log.w(TAG, "Progress achieved: " + progress + ", seeking to millis: " + c);
                    if (player != null)
                        player.seekToMillis(c);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        runSeekChanger();
    }

    private void setFullscreen(boolean isToFullscreen) {
        setRequestedOrientation(isToFullscreen ?
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isFullScreen = isToFullscreen;
    }

    @Override
    public void onBackPressed() {
        if (isFullScreen || !U.isDevicePortrait(getResources())) {
            setFullscreen(false);
        } else
            super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.isFullScreen = !U.isDevicePortrait(getResources());
    }

    void openYoutubeSampleActivity() {
        Intent intent = new Intent(this, YoutubePLayListerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        /*this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);*/
        Intent intent = getIntent();
        if (intent.hasExtra("video")) {
            video = (VideoItem) intent.getSerializableExtra("video");
            if (video == null) {
                finish();
                return;
            }
        } else {
            finish();
            return;
        }

        // Initializing YouTube player view
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        findViewById(R.id.btnPlay).setOnClickListener(this);

        showControls(true);
        rlControlHolder.setVisibility(View.VISIBLE);
        seekBar = findViewById(R.id.seekBarVideo);
        seekHandler = new Handler();

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (player == null) return;

        /* start buffering */
        if (!wasRestored) {
            player.cueVideo(video.getId());
        }

        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_SHORT).show();
    }

    private ProgressBar findProgressBar(View view) {
        try {
            if (view instanceof ProgressBar) {
                return (ProgressBar) view;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    ProgressBar res = findProgressBar(viewGroup.getChildAt(i));
                    if (res != null) return res;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            mOutputText.setText(R.string.noInternetMsg);
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText("This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                MainActivity.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the YouTube Data API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    @SuppressLint("StaticFieldLeak")
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.youtube.YouTube mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.youtube.YouTube.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("YouTube Data API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call YouTube Data API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch information about the "GoogleDevelopers" YouTube channel.
         *
         * @return List of Strings containing information about the channel.
         * @throws IOException
         */
        @SuppressWarnings("JavaDoc")
        private List<String> getDataFromApi() throws IOException {
            // Get a list of up to 10 files.
            List<String> channelInfo = new ArrayList<>();
            ChannelListResponse result = mService.channels().list("snippet,contentDetails,statistics")
                    .setForUsername("GoogleDevelopers")
                    .execute();
            List<Channel> channels = result.getItems();
            if (channels != null) {
                Channel channel = channels.get(0);
                channelInfo.add("This channel's ID is " + channel.getId() + ". " +
                        "Its title is '" + channel.getSnippet().getTitle() + ", " +
                        "and it has " + channel.getStatistics().getViewCount() + " views.");
            }
            return channelInfo;
        }

        @Override
        protected void onPreExecute() {
            mOutputText.setText("");
            mProgress.show();
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                mOutputText.setText("No results returned.");
            } else {
                output.add(0, "Data retrieved using the YouTube Data API:");
                mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText(String.format("The following error occurred:\n%s", mLastError.getMessage()));
                }
            } else {
                mOutputText.setText("Request cancelled.");
            }
        }
    }
}
