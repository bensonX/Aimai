package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.Info;
import com.ins.aimai.bean.common.TestBean;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TimeUtil;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个adapter在首页资讯和我的收藏都有使用，两者功能一致，UI上有细微差别这里决定复用这个adapter，根据不同使用状态调整UI
 * 如果有layoutHelper 则是首页使用，否则是我的收藏
 */
public class RecycleAdapterHomeInfo extends DelegateAdapter.Adapter<RecycleAdapterHomeInfo.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<Info> results = new ArrayList<>();

    public List<Info> getResults() {
        return results;
    }

    public RecycleAdapterHomeInfo(Context context, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;
    }

    public RecycleAdapterHomeInfo(Context context) {
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_home_info;
    }

    @Override
    public RecycleAdapterHomeInfo.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterHomeInfo.Holder holder, final int position) {
        final Info info = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder);
            }
        });

        //首页和收藏的UI细节不同
        if (layoutHelper != null) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.leftMargin = DensityUtil.dp2px(context, 10);
            layoutParams.rightMargin = DensityUtil.dp2px(context, 10);
        } else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        }

        GlideUtil.loadImg(holder.img_item_info, R.drawable.default_bk_img, info.getImage());
        holder.text_item_info_title.setText(info.getTitle());
        holder.text_item_info_time.setText(TimeUtil.formatSecond(System.currentTimeMillis() - info.getCreateTime()) + "前");
        holder.text_item_info_favo.setText(info.getCollectNum() + "");
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView img_item_info;
        private TextView text_item_info_title;
        private TextView text_item_info_time;
        private TextView text_item_info_favo;

        public Holder(View itemView) {
            super(itemView);
            root = (LinearLayout) itemView.findViewById(R.id.root);
            img_item_info = (ImageView) itemView.findViewById(R.id.img_item_info);
            text_item_info_title = (TextView) itemView.findViewById(R.id.text_item_info_title);
            text_item_info_time = (TextView) itemView.findViewById(R.id.text_item_info_time);
            text_item_info_favo = (TextView) itemView.findViewById(R.id.text_item_info_favo);
        }
    }

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }
}
