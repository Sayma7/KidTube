package study.sayma.kidtube.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import study.sayma.kidtube.R;
import study.sayma.kidtube.adapters.VideoAdapter;
import study.sayma.kidtube.models.PlayListItem;
import study.sayma.kidtube.models.VideoItem;

/**
 * Created by Sayma on 11/23/2017.
 */

public class VideoListFragment extends Fragment {
    private static final String KEY_ITEM = "item";

    public static VideoListFragment newInstance(PlayListItem playListItem) {
        VideoListFragment f = new VideoListFragment();
        Bundle b = new Bundle();
        b.putSerializable(KEY_ITEM, playListItem);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_playlist_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rvVideos);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle arguments = getArguments();
        PlayListItem plItem = (PlayListItem)
                (arguments != null ? arguments.getSerializable(KEY_ITEM) : null);
        VideoAdapter videoAdapter = new VideoAdapter(
                getActivity(),
                plItem != null ? plItem.getVideoList() : new ArrayList<VideoItem>());
        rv.setAdapter(videoAdapter);
    }
}
