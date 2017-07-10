package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.BannerView;

import java.util.ArrayList;
import java.util.List;


public class RecycleAdapterCountDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int typeSrcHeader = R.layout.item_home_banner;
    private final int typeSrcContent = R.layout.item_home_info;

    private Context context;
    private List<TestBean> results = new ArrayList<>();
    private List<Image> resultsBanner = new ArrayList<>();
    private String title;

    public List<TestBean> getResults() {
        return results;
    }

    public List<Image> getResultsBanner() {
        return resultsBanner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RecycleAdapterCountDetail(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case typeSrcHeader:
                return new HolderHeader(LayoutInflater.from(parent.getContext()).inflate(typeSrcHeader, parent, false));
            case typeSrcContent:
                return new HolderContent(LayoutInflater.from(parent.getContext()).inflate(typeSrcContent, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HolderContent) {
            TestBean bean = results.get(holder.getLayoutPosition() - 1);
            ((HolderContent) holder).text_name.setText(bean.getName());
        }
        if (holder instanceof HolderHeader) {
            ((HolderHeader) holder).banner.setDatas(resultsBanner);
            ((HolderHeader) holder).banner.showTitle(false);
            ((HolderHeader) holder).banner.setOnLoadImgListener(new BannerView.OnLoadImgListener() {
                @Override
                public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                    GlideUtil.loadImg(context, imageView, defaultSrc, imgurl);
//            imageView.setImageResource(R.drawable.default_pic);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return typeSrcHeader;
        } else {
            return typeSrcContent;
        }
    }

    @Override
    public int getItemCount() {
        return results.size() + 1;
    }

    public class HolderHeader extends RecyclerView.ViewHolder {
        private BannerView banner;

        public HolderHeader(View itemView) {
            super(itemView);
            banner = (BannerView) itemView.findViewById(R.id.banner);
        }
    }

    public class HolderContent extends RecyclerView.ViewHolder {
        private TextView text_name;

        public HolderContent(View itemView) {
            super(itemView);
            text_name = (TextView) itemView.findViewById(R.id.text_name);
        }
    }
}
