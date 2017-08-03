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
import com.ins.aimai.common.AppHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterAnswerBoard extends RecyclerView.Adapter<RecycleAdapterAnswerBoard.Holder> {

    private Context context;
    private List<QuestionBean> results = new ArrayList<>();

    public List<QuestionBean> getResults() {
        return results;
    }

    public RecycleAdapterAnswerBoard(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterAnswerBoard.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_board, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterAnswerBoard.Holder holder, final int position) {
        final QuestionBean questionBean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.text_item_answerboard_num.setText(position + 1 + "");
        String answerStr = questionBean.getChooseStr();
        if (TextUtils.isEmpty(answerStr)) {
            //还未答题
            holder.lay_item_answerboard.setSelected(false);
            holder.text_item_answerboard_num.setSelected(false);
            holder.text_item_answerboard_answer.setText("");
        } else {
            holder.lay_item_answerboard.setSelected(true);
            holder.text_item_answerboard_num.setSelected(true);
            holder.text_item_answerboard_answer.setText(answerStr);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private View lay_item_answerboard;
        private TextView text_item_answerboard_num;
        private TextView text_item_answerboard_answer;

        public Holder(View itemView) {
            super(itemView);
            lay_item_answerboard = itemView.findViewById(R.id.lay_item_answerboard);
            text_item_answerboard_num = (TextView) itemView.findViewById(R.id.text_item_answerboard_num);
            text_item_answerboard_answer = (TextView) itemView.findViewById(R.id.text_item_answerboard_answer);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
