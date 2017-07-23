package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterFavoLesson extends RecyclerView.Adapter<RecycleAdapterFavoLesson.Holder> {

    private Context context;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterFavoLesson(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterFavoLesson.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me_favo_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterFavoLesson.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        GlideUtil.loadImgTest(holder.img_item_order_header);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_item_order_header;

        public Holder(View itemView) {
            super(itemView);
            img_item_order_header = (ImageView) itemView.findViewById(R.id.img_item_order_header);
        }
    }
}
