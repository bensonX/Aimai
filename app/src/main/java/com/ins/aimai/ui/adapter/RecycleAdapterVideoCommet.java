package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Comment;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterVideoCommet extends RecyclerView.Adapter<RecycleAdapterVideoCommet.Holder> {

    private Context context;
    private List<Comment> results = new ArrayList<>();
    //字体大小，默认中号
    private int sizeType = AppData.Constant.TEXTSIZE_MIDDLE;

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

        //动态设置字体大小
        switch (sizeType) {
            case AppData.Constant.TEXTSIZE_BIG:
                holder.text_comment_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_big_nomal));
                holder.text_comment_time.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_nomal_small));
                holder.text_comment_detail.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_big_nomal));
                break;
            case AppData.Constant.TEXTSIZE_MIDDLE:
                holder.text_comment_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_nomal));
                holder.text_comment_time.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_small));
                holder.text_comment_detail.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_nomal));
                break;
            case AppData.Constant.TEXTSIZE_SMALL:
                holder.text_comment_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_nomal_small));
                holder.text_comment_time.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_small_tiny));
                holder.text_comment_detail.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_nomal_small));
                break;
        }
    }

    public void setTextSize(int sizeType) {
        this.sizeType = sizeType;
        notifyDataSetChanged();
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
