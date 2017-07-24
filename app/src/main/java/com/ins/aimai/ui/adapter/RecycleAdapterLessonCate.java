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
import com.ins.aimai.bean.LessonCate;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.interfaces.OnLessonClickListener;
import com.ins.aimai.ui.activity.LessonActivity;
import com.ins.aimai.ui.activity.LessonDetailActivity;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLessonCate extends DelegateAdapter.Adapter<RecycleAdapterLessonCate.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<LessonCate> results = new ArrayList<>();

    public List<LessonCate> getResults() {
        return results;
    }

    public RecycleAdapterLessonCate(Context context, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_lesson_cate;
    }

    @Override
    public RecycleAdapterLessonCate.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLessonCate.Holder holder, final int position) {
        final LessonCate lessonCate = results.get(position);
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessonActivity.start(context, lessonCate.getId(), lessonCate.getCurriculumTypeName());
            }
        });
        Lesson lesson1 = null;
        Lesson lesson2 = null;
        if (!StrUtil.isEmpty(lessonCate.getLessons())) {
            lesson1 = lessonCate.getLessons().get(0);
        }
        if (lessonCate.getLessons().size() > 1) {
            lesson2 = lessonCate.getLessons().get(1);
        }
        setCallBack(holder.lay_lesson_cate1, lesson1);
        setCallBack(holder.lay_lesson_cate2, lesson2);
        holder.text_lessoncate_title.setText(lessonCate.getCurriculumTypeName());
        if (lesson1 != null) {
            holder.lay_lesson_cate1.setVisibility(View.VISIBLE);
            GlideUtil.loadImg(holder.img_lession_cate1, R.drawable.default_bk_img, lesson1.getCover());
            holder.text_lesson_cate_title1.setText(lesson1.getCurriculumName());
            holder.text_lesson_cate_count1.setText(SpannableStringUtil.create(context, new String[]{lesson1.getVideoNum() + "", "个视频课"}, new int[]{R.color.am_blue, R.color.com_text_light}));
            holder.text_lesson_cate_price1.setText("￥" + AppHelper.formatPrice(lesson1.getPrice()));
        } else {
            holder.lay_lesson_cate1.setVisibility(View.INVISIBLE);
        }
        if (lesson2 != null) {
            holder.lay_lesson_cate2.setVisibility(View.VISIBLE);
            GlideUtil.loadImg(holder.img_lession_cate2, R.drawable.default_bk_img, lesson2.getCover());
            holder.text_lesson_cate_title2.setText(lesson2.getCurriculumName());
            holder.text_lesson_cate_count2.setText(SpannableStringUtil.create(context, new String[]{lesson2.getVideoNum() + "", "个视频课"}, new int[]{R.color.am_blue, R.color.com_text_light}));
            holder.text_lesson_cate_price2.setText("￥" + AppHelper.formatPrice(lesson2.getPrice()));
        } else {
            holder.lay_lesson_cate2.setVisibility(View.INVISIBLE);
        }
    }

    private void setCallBack(View view, final Lesson lesson) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onLessonClick(lesson);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_lessoncate_title;
        private View lay_lesson_cate1;
        private View lay_lesson_cate2;
        private ImageView img_lession_cate1;
        private ImageView img_lession_cate2;
        private TextView text_lesson_cate_title1;
        private TextView text_lesson_cate_title2;
        private TextView text_lesson_cate_count1;
        private TextView text_lesson_cate_count2;
        private TextView text_lesson_cate_price1;
        private TextView text_lesson_cate_price2;
        private View btn_more;

        public Holder(View itemView) {
            super(itemView);
            text_lessoncate_title = (TextView) itemView.findViewById(R.id.text_lessoncate_title);
            lay_lesson_cate1 = itemView.findViewById(R.id.lay_lesson_cate1);
            lay_lesson_cate2 = itemView.findViewById(R.id.lay_lesson_cate2);
            img_lession_cate1 = (ImageView) itemView.findViewById(R.id.img_lesson_cate1);
            img_lession_cate2 = (ImageView) itemView.findViewById(R.id.img_lession_cate2);
            text_lesson_cate_title1 = (TextView) itemView.findViewById(R.id.text_lesson_cate_title1);
            text_lesson_cate_title2 = (TextView) itemView.findViewById(R.id.text_lesson_cate_title2);
            text_lesson_cate_count1 = (TextView) itemView.findViewById(R.id.text_lesson_cate_count1);
            text_lesson_cate_count2 = (TextView) itemView.findViewById(R.id.text_lesson_cate_count2);
            text_lesson_cate_price1 = (TextView) itemView.findViewById(R.id.text_lesson_cate_price1);
            text_lesson_cate_price2 = (TextView) itemView.findViewById(R.id.text_lesson_cate_price2);
            btn_more = itemView.findViewById(R.id.btn_more);
        }
    }


    private OnLessonClickListener listener;

    public void setOnLessonClickListener(OnLessonClickListener onLessonClickListener) {
        this.listener = onLessonClickListener;
    }
}
