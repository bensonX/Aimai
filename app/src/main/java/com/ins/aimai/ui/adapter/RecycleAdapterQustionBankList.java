package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.ExamPractice;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.view.RectBackTextView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterQustionBankList extends RecyclerView.Adapter<RecycleAdapterQustionBankList.Holder> {

    private Context context;
    private List<ExamPractice> results = new ArrayList<>();
    private int type;

    public List<ExamPractice> getResults() {
        return results;
    }

    public RecycleAdapterQustionBankList(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public RecycleAdapterQustionBankList.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionbank_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterQustionBankList.Holder holder, final int position) {
        final ExamPractice examPractice = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });

        holder.text_questionbank_title.setText(examPractice.getCourseWareName());
        holder.text_questionbank_count_question.setText(SpannableStringUtil.create(context, new String[]{"共 " , examPractice.getExaminationNum() + "", " 题"}, new int[]{R.color.com_text_dark, R.color.am_blue, R.color.com_text_dark}));
        if (type == 0) {
            if (examPractice.getIsExamination() == 0) {
                holder.text_questionbank_status.setVisibility(View.GONE);
            } else {
                holder.text_questionbank_status.setVisibility(View.VISIBLE);
            }
            holder.text_questionbank_count_error.setVisibility(View.GONE);
        } else {
            holder.text_questionbank_status.setVisibility(View.GONE);
            holder.text_questionbank_count_error.setVisibility(View.VISIBLE);
            holder.text_questionbank_count_error.setText(SpannableStringUtil.create(context, new String[]{"错误 ", examPractice.getErrorNum() + "", " 题"}, new int[]{R.color.com_text_dark, R.color.am_yellow, R.color.com_text_dark}));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_questionbank_title;
        private RectBackTextView text_questionbank_status;
        private TextView text_questionbank_count_question;
        private TextView text_questionbank_count_error;

        public Holder(View itemView) {
            super(itemView);
            text_questionbank_title = (TextView) itemView.findViewById(R.id.text_questionbank_title);
            text_questionbank_status = (RectBackTextView) itemView.findViewById(R.id.text_questionbank_status);
            text_questionbank_count_question = (TextView) itemView.findViewById(R.id.text_questionbank_count_question);
            text_questionbank_count_error = (TextView) itemView.findViewById(R.id.text_questionbank_count_error);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
