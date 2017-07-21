package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.bean.Trade;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterTrade extends RecyclerView.Adapter<RecycleAdapterTrade.Holder> {

    private Context context;
    private List<Trade> results = new ArrayList<>();

    public List<Trade> getResults() {
        return results;
    }

    public RecycleAdapterTrade(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterTrade.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trade, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterTrade.Holder holder, final int position) {
        final Trade bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });

        holder.text_item_trade.setText(bean.getTradeName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_item_trade;

        public Holder(View itemView) {
            super(itemView);
            text_item_trade = (TextView) itemView.findViewById(R.id.text_item_trade);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
