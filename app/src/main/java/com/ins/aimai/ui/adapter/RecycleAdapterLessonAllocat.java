package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个adapter在首页资讯和我的收藏都有使用，两者功能一致，UI上有细微差别这里决定复用这个adapter，根据不同使用状态调整UI
 * 如果有layoutHelper 则是首页使用，否则是我的收藏
 */
public class RecycleAdapterLessonAllocat extends DelegateAdapter.Adapter<RecycleAdapterLessonAllocat.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<Lesson> results = new ArrayList<>();

    public List<Lesson> getResults() {
        return results;
    }

    public RecycleAdapterLessonAllocat(Context context, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;
    }

    public RecycleAdapterLessonAllocat(Context context) {
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_lesson_allocat;
    }

    @Override
    public RecycleAdapterLessonAllocat.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLessonAllocat.Holder holder, final int position) {
        final Lesson lesson = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lesson.setSelect(!lesson.isSelect());
                notifyItemChanged(position);
            }
        });

        //首页和收藏的UI细节不同
        if (layoutHelper != null) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.leftMargin = DensityUtil.dp2px(context, 10);
            layoutParams.rightMargin = DensityUtil.dp2px(context, 10);
        } else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        }

        GlideUtil.loadImg(holder.img_item_lessonalloct_header, R.drawable.default_bk_img, lesson.getCover());
        holder.text_item_lessonalloct_title.setText(lesson.getCurriculumName());
        holder.text_item_lessonalloct_count_video.setText(lesson.getVideoNum() + "");
        holder.text_item_lessonalloct_count_lesson.setText(lesson.getNumber() + "");
        holder.img_check.setSelected(lesson.isSelect());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public String getSelectedIds() {
        String ids = "";
        for (Lesson lesson : results) {
            if (lesson.isSelect()) {
                ids += lesson.getId() + ",";
            }
        }
        ids = StrUtil.subLastChart(ids, ",");
        return ids;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_check;
        private ImageView img_item_lessonalloct_header;
        private TextView text_item_lessonalloct_title;
        private TextView text_item_lessonalloct_count_video;
        private TextView text_item_lessonalloct_count_lesson;

        public Holder(View itemView) {
            super(itemView);
            img_check = (ImageView) itemView.findViewById(R.id.img_check);
            img_item_lessonalloct_header = (ImageView) itemView.findViewById(R.id.img_item_lessonalloct_header);
            text_item_lessonalloct_title = (TextView) itemView.findViewById(R.id.text_item_lessonalloct_title);
            text_item_lessonalloct_count_video = (TextView) itemView.findViewById(R.id.text_item_lessonalloct_count_video);
            text_item_lessonalloct_count_lesson = (TextView) itemView.findViewById(R.id.text_item_lessonalloct_count_lesson);
        }
    }
}
