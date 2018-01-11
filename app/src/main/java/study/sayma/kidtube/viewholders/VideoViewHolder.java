package study.sayma.kidtube.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import study.sayma.kidtube.R;


public class VideoViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivThumb;
    public TextView tvName, tvTime;

    public VideoViewHolder(View itemView) {
        super(itemView);
        ivThumb = itemView.findViewById(R.id.ivThumbVideo);
        tvName = itemView.findViewById(R.id.tvNameVideo);
        tvTime = itemView.findViewById(R.id.tvTimeVideo);
    }
}
