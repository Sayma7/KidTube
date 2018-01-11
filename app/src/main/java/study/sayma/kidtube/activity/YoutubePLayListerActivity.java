package study.sayma.kidtube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.LinkagePager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONException;
import org.json.JSONObject;
import study.sayma.kidtube.R;
import java.util.ArrayList;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.LinkagePagerContainer;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import study.sayma.kidtube.api.FileGetter;
import study.sayma.kidtube.fragments.CoverFragment;
import study.sayma.kidtube.fragments.VideoListFragment;
import study.sayma.kidtube.interfaces.ApiCallback;
import study.sayma.kidtube.models.PlayListItem;
import study.sayma.kidtube.services.FloatingPlayerService;
import study.sayma.kidtube.utils.P;
import study.sayma.kidtube.utils.U;

import static study.sayma.kidtube.activity.MainActivity.API_KEY;

public class YoutubePLayListerActivity extends AppCompatActivity {

    public static final String PL_DATA = "_playlist";
    public static final String API_KEY = "AIzaSyA0Ob1g4gfL7bSi6ui2j0v5oJU5QqWcGZY";
    private static final String TAG = YoutubePLayListerActivity.class.getSimpleName();
    private View pbLoader;
    private PlayListItem plList;

    private CoverPagerAdapter coverAdapter;
    private MyListPagerAdapter listPagerAdapter;
    private ApiCallback listLoader = new ApiCallback() {

        @Override
        public void onStart() {
            pbLoader.setVisibility(View.VISIBLE);
            Log.d(TAG, "starting api request ...");
        }

        @Override
        public void onSuccess(String response) {
            pbLoader.setVisibility(View.GONE);
            Log.w(TAG, "response got: " + response);
            if (response == null) {
                toast("Play-list loading failed!");
                return;
            }

            try {
                JSONObject jo = new JSONObject(response);
                ArrayList<PlayListItem> playlist =
                        PlayListItem.parseList(jo.getJSONArray("playlist"));
                for (PlayListItem pli : playlist)
                    Log.d(TAG, "-> " + pli.toString());

                // TODO Set play-list in cover-flow & video-list
                coverAdapter.setItems(playlist);
                listPagerAdapter.setItems(playlist);
            } catch (JSONException e) {
                e.printStackTrace();
                toast("File-parsing failed");
            }
        }

        @Override
        public void onError(String message) {
            pbLoader.setVisibility(View.GONE);
            Log.w(TAG, "error occurred: " + message);
            toast(message);
        }
    };

    private LinkagePagerContainer customPagerContainer;
    private LinkagePager pager;
    private AppBarLayout appBarLayout;
    private int parallaxHeight;
    private View tab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yt);
        P.assurePrefNotNull(YoutubePLayListerActivity.this);
        P.setData(PL_DATA, "");

        pbLoader = findViewById(R.id.pbLoader);
        pbLoader.setVisibility(View.GONE);
        setupPagers();

        if (U.isNetConnected(this)) {
            pbLoader.setVisibility(View.VISIBLE);
            new FileGetter(listLoader).execute();
        } else
            toast("No internet connection");

        addControls();
    }
    private void addControls(){

        ImageView btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(YoutubePLayListerActivity.this, MainActivity.class) ;
            startActivity(intent);
        });

        FloatingActionButton fabPlayAll = findViewById(R.id.fabPlayAll);
        fabPlayAll.setOnClickListener((View v) -> {

        });
    }

    private void setupPagers() {
        int coverHeight = getResources().getDimensionPixelSize(R.dimen.cover_pager_height);
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        parallaxHeight = coverHeight - tabHeight;

        Log.d("###", "parallaxHeight:" + parallaxHeight);
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            // Log.d("###","verticalOffset: " + Math.abs(verticalOffset));
            if (Math.abs(verticalOffset) >= parallaxHeight) {
                tab.setVisibility(View.VISIBLE);
            } else {
                tab.setVisibility(View.GONE);
            }
        });

        customPagerContainer = findViewById(R.id.pager_container);
        customPagerContainer.setPageItemClickListener((view, position) -> pager.setCurrentItem(position));

        tab = findViewById(R.id.tab);

        final LinkagePager cover = customPagerContainer.getViewPager();

        coverAdapter = new CoverPagerAdapter(getSupportFragmentManager(),
                new ArrayList<PlayListItem>());
        cover.setAdapter(coverAdapter);
        cover.setOffscreenPageLimit(5);

        new CoverFlow.Builder()
                .withLinkage(cover)
                .pagerMargin(0f)
                .scale(0.3f)
                .spaceSize(0f)
                .build();


        pager = findViewById(R.id.pager);
        listPagerAdapter = new MyListPagerAdapter(
                getSupportFragmentManager(), new ArrayList<PlayListItem>());

        pager.setOffscreenPageLimit(5);
        pager.setAdapter(listPagerAdapter);

        cover.setLinkagePager(pager);
        pager.setLinkagePager(cover);
    }

    private void toast(String msg) {
        Toast.makeText(YoutubePLayListerActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private class MyListPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<PlayListItem> plList;

        public MyListPagerAdapter(FragmentManager fm, ArrayList<PlayListItem> plList) {
            super(fm);
            this.plList = plList;
        }

        public void setItems(ArrayList<PlayListItem> plItems) {
            this.plList = plItems;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return VideoListFragment.newInstance(plList.get(position));
        }

        @Override
        public int getCount() {
            return plList.size();
        }
    }

    private class CoverPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<PlayListItem> plList;

        public CoverPagerAdapter(FragmentManager fm, ArrayList<PlayListItem> plList) {
            super(fm);
            this.plList = plList;
        }

        public void setItems(ArrayList<PlayListItem> plItems) {
            this.plList = plItems;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            if (position < 0 || position >= plList.size())
                return null;
            return CoverFragment.newInstance(plList.get(position));
        }

        @Override
        public int getCount() {
            return plList.size();
        }
    }
}
