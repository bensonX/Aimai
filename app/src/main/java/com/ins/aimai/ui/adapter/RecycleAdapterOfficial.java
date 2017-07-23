package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterOfficial extends RecyclerView.Adapter<RecycleAdapterOfficial.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterOfficial(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterOfficial.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_official, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterOfficial.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
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

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
