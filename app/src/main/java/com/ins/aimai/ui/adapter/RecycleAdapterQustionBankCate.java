package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Study;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.SpannableStringUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterQustionBankCate extends RecyclerView.Adapter<RecycleAdapterQustionBankCate.Holder> {

    private Context context;
    private List<Study> results = new ArrayList<>();
    private int type;

    public List<Study> getResults() {
        return results;
    }

    public RecycleAdapterQustionBankCate(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public RecycleAdapterQustionBankCate.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionbank_cate, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterQustionBankCate.Holder holder, final int position) {
        final Study study = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.text_questionbank_cate_title.setText(study.getCurriculumName());
        holder.text_questionbank_cate_count_video.setText(SpannableStringUtil.create(context, new String[]{study.getVideoNum() + "", " 个视频课"}, new int[]{R.color.am_blue, R.color.com_text_dark}));
        if (type == 0) {
            holder.text_questionbank_cate_count_question.setVisibility(View.GONE);
        } else {
            holder.text_questionbank_cate_count_question.setVisibility(View.VISIBLE);
            holder.text_questionbank_cate_count_question.setText(SpannableStringUtil.create(context, new String[]{"错误 " , study.getVideoNum() + "", " 题"}, new int[]{R.color.com_text_dark, R.color.am_yellow, R.color.com_text_dark}));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_questionbank_cate_title;
        private TextView text_questionbank_cate_count_video;
        private TextView text_questionbank_cate_count_question;

        public Holder(View itemView) {
            super(itemView);
            text_questionbank_cate_title = (TextView) itemView.findViewById(R.id.text_questionbank_cate_title);
            text_questionbank_cate_count_video = (TextView) itemView.findViewById(R.id.text_questionbank_cate_count_video);
            text_questionbank_cate_count_question = (TextView) itemView.findViewById(R.id.text_questionbank_cate_count_question);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
