package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ins.aimai.R;
import com.ins.aimai.bean.SortBean;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterSortUser extends RecyclerView.Adapter<RecycleAdapterSortUser.MyRecycleHolder> {

    private List<SortBean> results;
    private Context context;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();

    public List<SortBean> getResults() {
        return results;
    }

    public RecycleAdapterSortUser(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    public void addAll(List<SortBean> results){
        results.addAll(results);
    }

    public void add(SortBean bean, int position) {
        results.add(position, bean);
        notifyItemInserted(position);
    }

    @Override
    public MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_user, parent, false);
        return new MyRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleHolder holder, int position) {
        if (results == null || results.size() == 0 || results.size() <= position)
            return;
        SortBean bean = results.get(position);
        if (bean != null) {
            holder.tv_name.setText(bean.getNameSmart());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(bean.getName().charAt(0)), mColorGenerator.getColor(bean.getName()));
            holder.iv_img.setImageDrawable(drawable);
        }
    }

//    @Override
//    public void onBindViewHolder(MyRecycleHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class MyRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView tv_name;
        public final ImageView iv_img;

        public MyRecycleHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
