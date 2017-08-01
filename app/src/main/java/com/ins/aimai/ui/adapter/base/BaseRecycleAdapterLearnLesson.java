package com.ins.aimai.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ins.aimai.bean.Study;
import com.ins.aimai.ui.adapter.RecycleAdapterLearnLesson;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public abstract class BaseRecycleAdapterLearnLesson<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public abstract List<Study> getResults();
    public abstract void setOnItemClickListener(OnRecycleItemClickListener listener);
}
