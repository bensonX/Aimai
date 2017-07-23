package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.activity.EmployAddActivity;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterEmploySearch extends RecyclerView.Adapter<RecycleAdapterEmploySearch.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterEmploySearch(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterEmploySearch.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employ_search, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterEmploySearch.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployAddActivity.start(context);
            }
        });
        GlideUtil.loadCircleImgTest(holder.img_employ_search_header);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_employ_search_header;
        private View btn_employ_search_add;

        public Holder(View itemView) {
            super(itemView);
            img_employ_search_header = (ImageView) itemView.findViewById(R.id.img_employ_search_header);
            btn_employ_search_add = itemView.findViewById(R.id.btn_employ_search_add);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
