package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.ExamModelOffi;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.StatusHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.view.RectBackTextView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterModelOffi extends RecyclerView.Adapter<RecycleAdapterModelOffi.Holder> {

    private Context context;
    private List<ExamModelOffi> results = new ArrayList<>();
    private int type;

    public List<ExamModelOffi> getResults() {
        return results;
    }

    public RecycleAdapterModelOffi(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public RecycleAdapterModelOffi.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modeloffi, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterModelOffi.Holder holder, final int position) {
        final ExamModelOffi bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.text_modeloffi_title.setText(bean.getCurriculumName());
        holder.text_modeloffi_count.setText(SpannableStringUtil.create(context, new String[]{"共 ", bean.getExaminationNum() + "", " 题"}, new int[]{R.color.com_text_dark, R.color.am_blue, R.color.com_text_dark}));
        if (type == 0) {
            //模拟考试
            StatusHelper.Exam.setModelStatus(holder.text_modeloffi_status, bean);
            holder.btn_modeloffi_redeal.setVisibility(View.GONE);
        } else {
            //正式考试
            StatusHelper.Exam.setOffiStatus(holder.text_modeloffi_status, bean);
            holder.btn_modeloffi_redeal.setVisibility(StatusHelper.Exam.needOffiRedeal(bean) ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private RectBackTextView text_modeloffi_status;
        private TextView text_modeloffi_title;
        private TextView text_modeloffi_count;
        private View btn_modeloffi_redeal;

        public Holder(View itemView) {
            super(itemView);
            text_modeloffi_status = (RectBackTextView) itemView.findViewById(R.id.text_modeloffi_status);
            text_modeloffi_title = (TextView) itemView.findViewById(R.id.text_modeloffi_title);
            text_modeloffi_count = (TextView) itemView.findViewById(R.id.text_modeloffi_count);
            btn_modeloffi_redeal = itemView.findViewById(R.id.btn_modeloffi_redeal);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
