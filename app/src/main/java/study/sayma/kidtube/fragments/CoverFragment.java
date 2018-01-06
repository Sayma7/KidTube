package study.sayma.kidtube.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import study.sayma.kidtube.R;
import study.sayma.kidtube.models.PlayListItem;

/**
 * Created by Sayma on 12/27/2017.
 */

public class CoverFragment extends Fragment {
    private static final String KEY_ITEM = "item";

    public static CoverFragment newInstance(PlayListItem playListItem) {
        CoverFragment f = new CoverFragment();
        Bundle b = new Bundle();
        b.putSerializable(KEY_ITEM, playListItem);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_cover_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        PlayListItem plItem = (PlayListItem)
                (arguments != null ? arguments.getSerializable(KEY_ITEM) : null);
        if (plItem == null)
            return;
        ((TextView) view.findViewById(R.id.tvNamePlaylist)).setText(plItem.getName());
        Glide.with(getActivity())
                .load(plItem.getThumbUrl())
                .into((ImageView) view.findViewById(R.id.ivThumbPlaylist));
    }
}
