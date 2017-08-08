package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.common.AppHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLesson extends RecyclerView.Adapter<RecycleAdapterLesson.Holder> {

    private Context context;
    private List<Lesson> results = new ArrayList<>();
    private int orientation;

    public List<Lesson> getResults() {
        return results;
    }

    public RecycleAdapterLesson(Context context) {
        this(context, OrientationHelper.VERTICAL);
    }

    public RecycleAdapterLesson(Context context, int orientation) {
        this.context = context;
        this.orientation = orientation;
    }

    @Override
    public RecycleAdapterLesson.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        int src = (orientation == OrientationHelper.VERTICAL ? R.layout.item_lession_v : R.layout.item_lession_h);
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(src, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLesson.Holder holder, final int position) {
        final Lesson lesson = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadImg(holder.img_lesson_cate, R.drawable.default_bk_img, lesson.getCover());
        holder.text_lesson_cate_title.setText(lesson.getCurriculumName());
        holder.text_lesson_cate_count.setText(SpannableStringUtil.create(context, new String[]{lesson.getVideoNum() + "", "个视频课"}, new int[]{R.color.am_blue, R.color.com_text_light}));
        holder.text_lesson_cate_price.setText("￥" + AppHelper.formatPrice(lesson.getPrice()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_lesson_cate;
        private TextView text_lesson_cate_title;
        private TextView text_lesson_cate_count;
        private TextView text_lesson_cate_price;

        public Holder(View itemView) {
            super(itemView);
            img_lesson_cate = (ImageView) itemView.findViewById(R.id.img_lesson_cate);
            text_lesson_cate_title = (TextView) itemView.findViewById(R.id.text_lesson_cate_title);
            text_lesson_cate_count = (TextView) itemView.findViewById(R.id.text_lesson_cate_count);
            text_lesson_cate_price = (TextView) itemView.findViewById(R.id.text_lesson_cate_price);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
