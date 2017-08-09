package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Comment;
import com.ins.aimai.common.AppHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterVideoCommet extends RecyclerView.Adapter<RecycleAdapterVideoCommet.Holder> {

    private Context context;
    private List<Comment> results = new ArrayList<>();

    public List<Comment> getResults() {
        return results;
    }

    public RecycleAdapterVideoCommet(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterVideoCommet.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterVideoCommet.Holder holder, final int position) {
        final Comment comment = results.get(position);
        GlideUtil.loadCircleImg(holder.img_comment_header, R.drawable.default_header_edit, comment.getAvatar());
        holder.text_comment_name.setText(AppHelper.Comment.getShadowName(comment.getUserName()));
        holder.text_comment_detail.setText(comment.getContent());
        holder.text_comment_time.setText(TimeUtil.formatTimeRangeBefore(comment.getTimeStamp() - comment.getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_comment_header;
        private TextView text_comment_name;
        private TextView text_comment_time;
        private TextView text_comment_detail;

        public Holder(View itemView) {
            super(itemView);
            img_comment_header = (ImageView) itemView.findViewById(R.id.img_comment_header);
            text_comment_name = (TextView) itemView.findViewById(R.id.text_comment_name);
            text_comment_time = (TextView) itemView.findViewById(R.id.text_comment_time);
            text_comment_detail = (TextView) itemView.findViewById(R.id.text_comment_detail);
        }
    }
}
