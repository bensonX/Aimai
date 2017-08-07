package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLable extends RecyclerView.Adapter<RecycleAdapterLable.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<String> results = new ArrayList<>();

    public List<String> getResults() {
        return results;
    }

    public RecycleAdapterLable(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLable.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lable, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLable.Holder holder, final int position) {
        final String name = results.get(position);
        holder.text_lessondetail_lable.setText(name);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_lessondetail_lable;

        public Holder(View itemView) {
            super(itemView);
            text_lessondetail_lable = (TextView) itemView.findViewById(R.id.text_lessondetail_lable);
        }
    }
}
