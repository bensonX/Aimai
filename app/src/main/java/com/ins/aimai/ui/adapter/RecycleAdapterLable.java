package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLable extends RecyclerView.Adapter<RecycleAdapterLable.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
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
        final TestBean bean = results.get(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

//        private ImageView img_lession;

        public Holder(View itemView) {
            super(itemView);
//            img_lession = (ImageView) itemView.findViewById(R.id.img_lession);
        }
    }
}
