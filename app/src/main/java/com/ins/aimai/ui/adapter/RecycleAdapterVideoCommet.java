package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterVideoCommet extends RecyclerView.Adapter<RecycleAdapterVideoCommet.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterVideoCommet(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterVideoCommet.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterVideoCommet.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        GlideUtil.LoadCircleImgTest(holder.img_comment_header);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_comment_header;

        public Holder(View itemView) {
            super(itemView);
            img_comment_header = (ImageView) itemView.findViewById(R.id.img_comment_header);
        }
    }
}
