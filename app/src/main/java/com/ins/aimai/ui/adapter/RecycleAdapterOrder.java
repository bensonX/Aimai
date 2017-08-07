package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Order;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.PayDialogActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecycleAdapterOrder extends RecyclerView.Adapter<RecycleAdapterOrder.Holder> {

    private Context context;
    private List<Order> results = new ArrayList<>();

    public List<Order> getResults() {
        return results;
    }

    public RecycleAdapterOrder(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterOrder.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_order, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterOrder.Holder holder, final int position) {
        final Order order = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.btn_item_order_gopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayDialogActivity.startRepay(context, order.getId());
            }
        });
        holder.btn_item_order_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOrderBtnClickListener != null) onOrderBtnClickListener.onDelClick(order);
            }
        });

        GlideUtil.loadImg(holder.img_item_order_header, R.drawable.default_bk_img, order.getCover());
        if (order.getPayStatus() == 0) {
            holder.text_item_order_title.setText("待付款");
            holder.text_item_order_title.setTextColor(ContextCompat.getColor(context, R.color.am_blue));
            holder.btn_item_order_gopay.setVisibility(View.VISIBLE);
            holder.btn_item_order_del.setVisibility(View.VISIBLE);
        } else {
            holder.text_item_order_title.setText("已完成");
            holder.text_item_order_title.setTextColor(ContextCompat.getColor(context, R.color.com_text_dark_blank));
            holder.btn_item_order_gopay.setVisibility(View.INVISIBLE);
            holder.btn_item_order_del.setVisibility(View.INVISIBLE);
        }
        holder.text_item_order_name.setText(order.getCurriculumName());
        holder.text_item_order_price.setText("￥" + AppHelper.formatPrice(order.getPrice()));
        holder.text_item_order_count.setText("x" + order.getNumber());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_item_order_header;
        private TextView text_item_order_title;
        private TextView text_item_order_name;
        private TextView text_item_order_price;
        private TextView text_item_order_count;
        private TextView btn_item_order_gopay;
        private TextView btn_item_order_del;

        public Holder(View itemView) {
            super(itemView);
            img_item_order_header = (ImageView) itemView.findViewById(R.id.img_item_order_header);
            text_item_order_title = (TextView) itemView.findViewById(R.id.text_item_order_title);
            text_item_order_name = (TextView) itemView.findViewById(R.id.text_item_order_name);
            text_item_order_price = (TextView) itemView.findViewById(R.id.text_item_order_price);
            text_item_order_count = (TextView) itemView.findViewById(R.id.text_item_order_count);
            btn_item_order_gopay = (TextView) itemView.findViewById(R.id.btn_item_order_gopay);
            btn_item_order_del = (TextView) itemView.findViewById(R.id.btn_item_order_del);
        }
    }

    private OnOrderBtnClickListener onOrderBtnClickListener;

    public void setOnOrderBtnClickListener(OnOrderBtnClickListener onOrderBtnClickListener) {
        this.onOrderBtnClickListener = onOrderBtnClickListener;
    }

    public interface OnOrderBtnClickListener {
        void onDelClick(Order order);
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
