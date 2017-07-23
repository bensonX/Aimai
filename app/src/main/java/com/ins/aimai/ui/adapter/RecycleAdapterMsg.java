package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterMsg extends RecyclerView.Adapter<RecycleAdapterMsg.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterMsg(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterMsg.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterMsg.Holder holder, final int position) {
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
