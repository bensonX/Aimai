package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.Msg;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.databinding.ItemMsgBinding;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecycleAdapterMsg extends RecyclerView.Adapter<RecycleAdapterMsg.Holder> {

    private Context context;
    private List<Msg> results = new ArrayList<>();

    public List<Msg> getResults() {
        return results;
    }

    public RecycleAdapterMsg(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterMsg.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemMsgBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_msg, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterMsg.Holder holder, final int position) {
        final Msg msg = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.binding.setMsg(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemMsgBinding binding;

        public Holder(ItemMsgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
