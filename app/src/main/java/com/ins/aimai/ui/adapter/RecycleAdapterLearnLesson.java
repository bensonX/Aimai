package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Study;
import com.ins.aimai.ui.adapter.base.BaseRecycleAdapterLearnLesson;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLearnLesson extends BaseRecycleAdapterLearnLesson<RecycleAdapterLearnLesson.Holder> {

    private Context context;
    private List<Study> results = new ArrayList<>();

    public List<Study> getResults() {
        return results;
    }

    public RecycleAdapterLearnLesson(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLearnLesson.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLearnLesson.Holder holder, final int position) {
        final Study study = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadImg(holder.img_item_study_header, R.drawable.default_bk_img, study.getCover());
        holder.text_item_study_title.setText(study.getCurriculumName());
        holder.text_item_study_learntime.setText("已学习" + TimeUtil.formatSecond(study.getFinishSeconds()));
        holder.text_item_study_totaltime.setText("共" + TimeUtil.formatSecond(study.getVideoSeconds()));
        holder.progress.setProgress((int) ((float) study.getFinishSeconds() / (float) study.getVideoSeconds() * 100));
        String note = study.getExaminationNum() + "习题 " + study.getVideoNum() + "个视频课 " + study.getPptNum() + "个讲义";
        holder.text_item_study_note.setText(note);
        if (!study.isFinish() && !study.isPass()) {
            holder.img_learn_study_flag_pass.setVisibility(View.GONE);
        } else {
            holder.img_learn_study_flag_pass.setVisibility(View.VISIBLE);
            if (study.isFinish()) {
                holder.img_learn_study_flag_pass.setImageResource(R.drawable.ic_learn_flag_top_learned);
            }
            if (study.isPass()) {
                holder.img_learn_study_flag_pass.setImageResource(R.drawable.ic_learn_flag_top_pass);
            }
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_learn_study_flag_pass;
        private ImageView img_item_study_header;
        private TextView text_item_study_title;
        private TextView text_item_study_note;
        private TextView text_item_study_learntime;
        private TextView text_item_study_totaltime;
        private ProgressBar progress;

        public Holder(View itemView) {
            super(itemView);
            img_learn_study_flag_pass = (ImageView) itemView.findViewById(R.id.img_learn_study_flag_pass);
            img_item_study_header = (ImageView) itemView.findViewById(R.id.img_item_study_header);
            text_item_study_title = (TextView) itemView.findViewById(R.id.text_item_study_title);
            text_item_study_note = (TextView) itemView.findViewById(R.id.text_item_study_note);
            text_item_study_learntime = (TextView) itemView.findViewById(R.id.text_item_study_learntime);
            text_item_study_totaltime = (TextView) itemView.findViewById(R.id.text_item_study_totaltime);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
