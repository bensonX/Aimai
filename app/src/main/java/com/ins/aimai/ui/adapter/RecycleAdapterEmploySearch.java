package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.ui.activity.EmployAddActivity;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterEmploySearch extends RecyclerView.Adapter<RecycleAdapterEmploySearch.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<User> results = new ArrayList<>();

    public List<User> getResults() {
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
        final User user = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        holder.btn_employ_search_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployAddActivity.start(context, user);
            }
        });
        GlideUtil.loadCircleImg(holder.img_employ_search_header, R.drawable.default_header_edit, user.getAvatar());
        holder.text_employ_search_name.setText(user.getShowName());
        holder.text_employ_search_phone.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_employ_search_header;
        private TextView text_employ_search_name;
        private TextView text_employ_search_phone;
        private View btn_employ_search_add;

        public Holder(View itemView) {
            super(itemView);
            img_employ_search_header = (ImageView) itemView.findViewById(R.id.img_employ_search_header);
            text_employ_search_name = (TextView) itemView.findViewById(R.id.text_employ_search_name);
            text_employ_search_phone = (TextView) itemView.findViewById(R.id.text_employ_search_phone);
            btn_employ_search_add = itemView.findViewById(R.id.btn_employ_search_add);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
