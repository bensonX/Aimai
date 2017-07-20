package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLearnComp extends RecyclerView.Adapter<RecycleAdapterLearnComp.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterLearnComp(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterLearnComp.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_comp, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLearnComp.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });
        GlideUtil.loadImgTest(holder.img_item_learncomp);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_item_learncomp;

        public Holder(View itemView) {
            super(itemView);
            img_item_learncomp = (ImageView) itemView.findViewById(R.id.img_item_learncomp);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
