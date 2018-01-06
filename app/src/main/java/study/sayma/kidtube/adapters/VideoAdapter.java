package study.sayma.kidtube.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import study.sayma.kidtube.R;
import study.sayma.kidtube.activity.MainActivity;
import study.sayma.kidtube.models.VideoItem;
import study.sayma.kidtube.utils.U;
import study.sayma.kidtube.viewholders.VideoViewHolder;

/**
 * Created by Sayma on 12/27/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private Context context;
    private ArrayList<VideoItem> videoList;

    public VideoAdapter(Context context, ArrayList<VideoItem> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.video_item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        final VideoItem vi = getItem(position);
        Glide.with(context)
                .load(vi.getThumbUrl())
                .into(holder.ivThumb);
        holder.tvName.setText(vi.getName());
        holder.tvTime.setText(U.getSmallFormattedTime(vi.getLength()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("video", vi);
                context.startActivity(intent);
                final String video_id = intent.getStringExtra("vi");
            }
        });
    }

    public VideoItem getItem(int position) {
        if (position < 0 || position >= videoList.size())
            return null;
        return videoList.get(position);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
