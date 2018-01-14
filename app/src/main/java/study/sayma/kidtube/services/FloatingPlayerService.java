package study.sayma.kidtube.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;

import java.util.Arrays;

import study.sayma.kidtube.R;
import study.sayma.kidtube.activity.MainActivity;
import study.sayma.kidtube.models.VideoItem;
import study.sayma.kidtube.utils.U;

import static study.sayma.kidtube.activity.MainActivity.API_KEY;

/**
 * Created by Sayma on 1/8/2018.
 */

public class FloatingPlayerService extends Service {

    private static final String TAG = FloatingPlayerService.class.getSimpleName();
    private static final String[] SCOPES = {YouTubeScopes.YOUTUBE_READONLY};
    private WindowManager mWindowManager;
    private View floatingWindow;
    private VideoItem video;
    private long cueTime;

    private GoogleAccountCredential mCredential;
    private YouTubePlayer player;
    private YouTubePlayer.OnInitializedListener ytInitCb = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player2,
                                            boolean wasRestored) {
            FloatingPlayerService.this.player = player2;
            // player.setPlayerStateChangeListener(playerStateChangeListener);
            // player.setPlaybackEventListener(playbackEventListener);

        /* start buffering */
            if (!wasRestored) {
                player.cueVideo(video.getId(), (int) (cueTime/1000));
            } else
                playVideo();

            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                            YouTubeInitializationResult result) {
            Toast.makeText(FloatingPlayerService.this,
                    "Failed to initialize.", Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    };

    public FloatingPlayerService() {
    }

    private void pauseVideo() {
        ImageView btn = floatingWindow.findViewById(R.id.btnPlay);
        if (btn != null)
            pauseVideo(btn);
    }

    private void playVideo() {
        ImageView btn = floatingWindow.findViewById(R.id.btnPlay);
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
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onCreate() {
        super.onCreate();

        //Inflating the chat head layout we created
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        floatingWindow = null;
        if (inflater != null) {
            floatingWindow = inflater.inflate(R.layout.locked_player, null, false);
        }


        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                U.isAboveOreo() ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        : WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the chat head position
        params.gravity = Gravity.TOP | Gravity.START; //Initially view added at bottom-right corner
        params.x = 0;
        params.y = 0;/**/
        params.flags = params.flags | WindowManager.LayoutParams.FLAG_SECURE;

        floatingWindow.findViewById(R.id.btnScreenUnlock).setOnClickListener(this::playUnlockedVideo);
        floatingWindow.findViewById(R.id.rl_lock_holder).setVisibility(View.VISIBLE);

        // Initializing YouTube player view
        YouTubePlayerView youTubePlayerView = floatingWindow.findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, ytInitCb);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (mWindowManager != null) {
            mWindowManager.addView(floatingWindow, params);
        }
    }

    private void playUnlockedVideo(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("video", video);
        if (player != null)
            intent.putExtra("time", player.getCurrentTimeMillis());
        startActivity(intent);
        stopSelf();
    }

    // @IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        VideoItem video = (VideoItem) intent.getSerializableExtra("video");
        if (video == null) {
            stopSelf();
            return START_NOT_STICKY;
        }
        this.video = video;
        this.cueTime = intent.getIntExtra("time", 0);

        Log.e(TAG, "");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (floatingWindow != null) mWindowManager.removeView(floatingWindow);
        super.onDestroy();
    }
}

/*.setOnTouchListener(
                new View.OnTouchListener() {
                    private int lastAction;
                    private int initialX;
                    private int initialY;
                    private float initialTouchX;
                    private float initialTouchY;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:

                                //remember the initial position.
                                initialX = params.x;
                                initialY = params.y;

                                //get the touch location
                                initialTouchX = event.getRawX();
                                initialTouchY = event.getRawY();

                                lastAction = event.getAction();
                                return true;
                            case MotionEvent.ACTION_UP:
                                //As we implemented on touch listener with ACTION_MOVE,
                                //we have to check if the previous action was ACTION_DOWN
                                //to identify if the user clicked the view or not.
                                if (lastAction == MotionEvent.ACTION_DOWN) {
                                    //Open the chat conversation click.
                                    Intent intent = new Intent(FloatingPlayerService.this, TutEncPlayActivity.class);
                                    intent.putExtra(C.KEY_VIDEO, vidLi);
                                    intent.putExtra(C.KEY_NAME, mEncryptedFile.getName());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            *//*intent.putExtra(C.KEY_VIDEO, vidLi);
                            intent.putExtra(C.KEY_NAME, mEncryptedFile.getName());*//*
                                    startActivity(intent);

                                    //close the service and remove the chat heads
                                    stopSelf();
                                }
                                lastAction = event.getAction();
                                return true;
                            case MotionEvent.ACTION_MOVE:
                                //Calculate the X and Y coordinates of the view.
                                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                                params.y = initialY + (int) (event.getRawY() - initialTouchY);

                                //Update the layout with new X & Y coordinate
                                mWindowManager.updateViewLayout(floatingWindow, params);
                                lastAction = event.getAction();
                                return true;
                        }
                        return false;
                    }
                });*/