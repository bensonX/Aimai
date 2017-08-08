package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLearnUser extends RecyclerView.Adapter<RecycleAdapterLearnUser.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<User> results = new ArrayList<>();

    public List<User> getResults() {
        return results;
    }

    public RecycleAdapterLearnUser(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLearnUser.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_user, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLearnUser.Holder holder, final int position) {
        final User bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadCircleImgTest(holder.img_learn_user_header);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_learn_user_header;

        public Holder(View itemView) {
            super(itemView);
            img_learn_user_header = (ImageView) itemView.findViewById(R.id.img_learn_user_header);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
