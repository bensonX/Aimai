package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.activity.InfoActivity;
import com.ins.common.entity.Image;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.BannerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeBanner extends DelegateAdapter.Adapter<RecycleAdapterHomeBanner.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<Image> results = new ArrayList<>();

    public List<Image> getResults() {
        return results;
    }

    public RecycleAdapterHomeBanner(Context context, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_home_banner;
    }

    @Override
    public RecycleAdapterHomeBanner.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterHomeBanner.Holder holder, final int position) {
        holder.banner.setDatas(results);
        holder.item_home_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoActivity.start(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private BannerView banner;
        private View item_home_more;

        public Holder(View itemView) {
            super(itemView);
            banner = (BannerView) itemView.findViewById(R.id.banner);
            item_home_more = itemView.findViewById(R.id.item_home_more);
            banner.showTitle(false);
            banner.setOnLoadImgListener(new BannerView.OnLoadImgListener() {
                @Override
                public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                    GlideUtil.loadImg(imageView, defaultSrc, imgurl);
                }
            });
        }
    }
}
