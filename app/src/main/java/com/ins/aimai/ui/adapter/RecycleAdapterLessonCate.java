package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.activity.LessionActivity;
import com.ins.common.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterLessonCate extends DelegateAdapter.Adapter<RecycleAdapterLessonCate.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<TestBean> results = new ArrayList<>();

    public List<TestBean> getResults() {
        return results;
    }

    public RecycleAdapterLessonCate(Context context, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_lession_cate;
    }

    @Override
    public RecycleAdapterLessonCate.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterLessonCate.Holder holder, final int position) {
        final TestBean bean = results.get(position);
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LessionActivity.start(context);
            }
        });
        GlideUtil.LoadImgTest(holder.img_lession_cate1);
        GlideUtil.LoadImgTest(holder.img_lession_cate2);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView img_lession_cate1;
        private ImageView img_lession_cate2;
        private View btn_more;

        public Holder(View itemView) {
            super(itemView);
            img_lession_cate1 = (ImageView) itemView.findViewById(R.id.img_lession_cate1);
            img_lession_cate2 = (ImageView) itemView.findViewById(R.id.img_lession_cate2);
            btn_more = itemView.findViewById(R.id.btn_more);
        }
    }
}
