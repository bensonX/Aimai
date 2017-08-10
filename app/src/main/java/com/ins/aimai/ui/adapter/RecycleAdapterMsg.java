package com.ins.aimai.ui.adapter;

import android.content.Context;
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
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecycleAdapterMsg extends RecyclerView.Adapter<RecycleAdapterMsg.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<Msg> results = new ArrayList<>();

    public List<Msg> getResults() {
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
        final Msg msg = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.text_item_msg_title.setText(msg.getTitle());
        holder.text_item_msg_time.setText(TimeUtil.getTimeFor("yyyy-mm-dd HH:mm", new Date(msg.getCreateTime())));
        holder.text_item_msg_content.setText(msg.getDigest());
        if (!msg.isChecked()) {
            //未看过的消息
            holder.text_item_msg_title.setTextColor(ContextCompat.getColor(context, R.color.com_text_blank_deep_light));
            holder.text_item_msg_time.setTextColor(ContextCompat.getColor(context, R.color.com_text_dark_light));
            holder.text_item_msg_content.setTextColor(ContextCompat.getColor(context, R.color.com_text_blank));
        } else {
            //看过的消息
            holder.text_item_msg_title.setTextColor(ContextCompat.getColor(context, R.color.com_text_dark_light));
            holder.text_item_msg_time.setTextColor(ContextCompat.getColor(context, R.color.com_text_light));
            holder.text_item_msg_content.setTextColor(ContextCompat.getColor(context, R.color.com_text_dark_light));
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView text_item_msg_title;
        private TextView text_item_msg_time;
        private TextView text_item_msg_content;

        public Holder(View itemView) {
            super(itemView);
            text_item_msg_title = (TextView) itemView.findViewById(R.id.text_item_msg_title);
            text_item_msg_time = (TextView) itemView.findViewById(R.id.text_item_msg_time);
            text_item_msg_content = (TextView) itemView.findViewById(R.id.text_item_msg_content);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
