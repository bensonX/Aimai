package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Study;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.ui.adapter.base.BaseRecycleAdapterLearnLesson;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLearnLessonComp extends BaseRecycleAdapterLearnLesson<RecycleAdapterLearnLessonComp.Holder> {

    private Context context;
    private List<Study> results = new ArrayList<>();

    public List<Study> getResults() {
        return results;
    }

    public RecycleAdapterLearnLessonComp(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLearnLessonComp.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_lesson_comp, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLearnLessonComp.Holder holder, final int position) {
        final Study study = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadImg(holder.img_item_study_header, R.drawable.default_bk_img, study.getCover());
        holder.text_item_study_title.setText(study.getCurriculumName());
        holder.text_item_study_videocount.setText(SpannableStringUtil.create(context, new String[]{study.getVideoNum() + "", "个视频课"}, new int[]{R.color.am_blue, R.color.com_text_dark_light}));
        holder.text_item_study_price.setText(SpannableStringUtil.createSize(new String[]{"￥", AppHelper.formatPrice(study.getPrice()) + ""}, new float[]{0.5f, 1f}));
        holder.text_item_study_countalloc.setText(SpannableStringUtil.create(context, new String[]{"已分配", study.getAllocationNum() + "", "份"}, new int[]{R.color.com_text_blank, R.color.am_blue, R.color.com_text_blank}));
        holder.text_item_study_countall.setText("共" + study.getNumber() + "份");
        holder.progress.setProgress((int) ((float) study.getFinishSeconds() / (float) study.getVideoSeconds() * 100));
        //TODO：公司没有已学完状态，这个标志隐藏掉
        holder.img_learn_study_flag_learned.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_learn_study_flag_learned;
        private ImageView img_item_study_header;
        private TextView text_item_study_title;
        private TextView text_item_study_videocount;
        private TextView text_item_study_price;
        private TextView text_item_study_countalloc;
        private TextView text_item_study_countall;
        private ProgressBar progress;

        public Holder(View itemView) {
            super(itemView);
            img_learn_study_flag_learned = (ImageView) itemView.findViewById(R.id.img_learn_study_flag_learned);
            img_item_study_header = (ImageView) itemView.findViewById(R.id.img_item_study_header);
            text_item_study_title = (TextView) itemView.findViewById(R.id.text_item_study_title);
            text_item_study_videocount = (TextView) itemView.findViewById(R.id.text_item_study_videocount);
            text_item_study_price = (TextView) itemView.findViewById(R.id.text_item_study_price);
            text_item_study_countalloc = (TextView) itemView.findViewById(R.id.text_item_study_countalloc);
            text_item_study_countall = (TextView) itemView.findViewById(R.id.text_item_study_countall);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
