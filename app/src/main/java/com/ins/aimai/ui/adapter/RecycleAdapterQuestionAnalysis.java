package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.view.AnswerView;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterQuestionAnalysis extends RecyclerView.Adapter<RecycleAdapterQuestionAnalysis.Holder> {

    private Context context;
    private List<QuestionBean> results = new ArrayList<>();
    private String lessonName;
    private String wareName;

    public List<QuestionBean> getResults() {
        return results;
    }

    public RecycleAdapterQuestionAnalysis(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterQuestionAnalysis.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_analysis, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterQuestionAnalysis.Holder holder, final int position) {
        final QuestionBean question = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.answerview.setData(question);
        holder.text_analysis_type.setText(question.getTypeName());
        holder.text_analysis_num.setText(position + 1 + "");
        holder.text_analysis_count.setText("/" + results.size());

        if (position==0) {
            if (!TextUtils.isEmpty(lessonName) || !TextUtils.isEmpty(wareName)) {
                holder.lay_analysis_cate.setVisibility(View.VISIBLE);
                holder.text_analysis_cate.setText(lessonName);
                holder.text_analysis_ware.setText("课时：" + wareName);
            } else {
                holder.lay_analysis_cate.setVisibility(View.GONE);
            }
        }else {
            holder.lay_analysis_cate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setTitle(String lessonName, String wareName) {
        this.lessonName = lessonName;
        this.wareName = wareName;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private AnswerView answerview;
        private TextView text_analysis_type;
        private TextView text_analysis_num;
        private TextView text_analysis_count;

        private View lay_analysis_cate;
        private TextView text_analysis_cate;
        private TextView text_analysis_ware;

        public Holder(View itemView) {
            super(itemView);
            answerview = (AnswerView) itemView.findViewById(R.id.answerview);
            text_analysis_type = (TextView) itemView.findViewById(R.id.text_analysis_type);
            text_analysis_num = (TextView) itemView.findViewById(R.id.text_analysis_num);
            text_analysis_count = (TextView) itemView.findViewById(R.id.text_analysis_count);

            lay_analysis_cate = itemView.findViewById(R.id.lay_analysis_cate);
            text_analysis_cate = (TextView) itemView.findViewById(R.id.text_analysis_cate);
            text_analysis_ware = (TextView) itemView.findViewById(R.id.text_analysis_ware);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
