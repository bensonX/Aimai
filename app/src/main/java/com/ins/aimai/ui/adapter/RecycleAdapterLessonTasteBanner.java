package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.List;

public class RecycleAdapterLessonTasteBanner extends DelegateAdapter.Adapter<RecycleAdapterLessonTasteBanner.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;

    private RecycleAdapterLessonTaste adapter;

    public List<TestBean> getResults() {
        return adapter.getResults();
    }

    public RecycleAdapterLessonTasteBanner(Context context, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        adapter = new RecycleAdapterLessonTaste(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_lession_tastebanner;
    }

    @Override
    public RecycleAdapterLessonTasteBanner.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLessonTasteBanner.Holder holder, final int position) {
        adapter.notifyDataSetChanged();
//        holder.text_name.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private RecyclerView recycler;

        public Holder(View itemView) {
            super(itemView);
            recycler = (RecyclerView) itemView.findViewById(R.id.recycler);
            recycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            recycler.setAdapter(adapter);
        }
    }

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        adapter.setOnItemClickListener(listener);
    }
}
