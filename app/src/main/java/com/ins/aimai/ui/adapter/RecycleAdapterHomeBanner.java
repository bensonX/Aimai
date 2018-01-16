package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.ui.activity.InfoActivity;
import com.ins.aimai.ui.activity.WebActivity;
import com.ins.common.entity.Image;
import com.ins.common.utils.GlideUtil;
import com.ins.common.view.BannerView;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapterHomeBanner extends DelegateAdapter.Adapter<RecycleAdapterHomeBanner.Holder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private List<Image> results = new ArrayList<>();

    private boolean isSpan = false;//是否折叠

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
        holder.btn_home_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoActivity.start(context);
            }
        });
        holder.text_home_span.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSpan = !isSpan;
                notifyItemChanged(0);
            }
        });
        holder.banner.setOnBannerClickListener(onBannerClickListener);

        holder.lay_home_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(context, "关于我们", NetApi.getBaseUrl() + AppData.Url.about);
            }
        });
        holder.lay_home_quali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(context, "资质", NetApi.getBaseUrl() + AppData.Url.quelity);
            }
        });
        holder.lay_home_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(context, "网点", NetApi.getBaseUrl() + AppData.Url.netpoint);
            }
        });

        if (isSpan){
            holder.lay_home_sort.setVisibility(View.GONE);
            holder.text_home_span.setText("展开");
        }else {
            holder.lay_home_sort.setVisibility(View.VISIBLE);
            holder.text_home_span.setText("收起");
        }

        holder.adapterProvince.clear();
        holder.adapterProvince.add("四川省");
        holder.adapterProvince.add("福建省");
        holder.adapterProvince.notifyDataSetChanged();
        holder.adapterCity.clear();
        holder.adapterCity.add("成都市");
        holder.adapterCity.add("宜宾市");
        holder.adapterCity.add("绵阳市");
        holder.adapterCity.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private BannerView banner;
        private TextView text_home_span;
        private View lay_home_about;
        private View lay_home_quali;
        private View lay_home_station;
        private View btn_home_more;
        private View lay_home_sort;
        private Spinner spinner_province;
        private Spinner spinner_city;

        private ArrayAdapter adapterProvince;
        private ArrayAdapter adapterCity;

        public Holder(View itemView) {
            super(itemView);
            lay_home_about = itemView.findViewById(R.id.lay_home_about);
            lay_home_quali = itemView.findViewById(R.id.lay_home_quali);
            lay_home_station = itemView.findViewById(R.id.lay_home_station);
            btn_home_more = itemView.findViewById(R.id.btn_home_more);
            lay_home_sort = itemView.findViewById(R.id.lay_home_sort);
            spinner_province = (Spinner) itemView.findViewById(R.id.spinner_province);
            text_home_span = (TextView) itemView.findViewById(R.id.text_home_span);
            spinner_city = (Spinner) itemView.findViewById(R.id.spinner_city);
            banner = (BannerView) itemView.findViewById(R.id.banner);

            banner.showTitle(false);
            banner.setOnLoadImgListener(new BannerView.OnLoadImgListener() {
                @Override
                public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                    GlideUtil.loadImg(imageView, defaultSrc, imgurl);
                }
            });
            adapterProvince = new ArrayAdapter<>(context, R.layout.lay_spinner_item, new ArrayList<String>());
            adapterProvince.setDropDownViewResource(R.layout.lay_spinner_item);
            adapterCity = new ArrayAdapter<>(context, R.layout.lay_spinner_item, new ArrayList<String>());
            adapterCity.setDropDownViewResource(R.layout.lay_spinner_item);
            spinner_province.setAdapter(adapterProvince);
            spinner_city.setAdapter(adapterCity);
        }
    }

    private BannerView.OnBannerClickListener onBannerClickListener;

    public void setOnBannerClickListener(BannerView.OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }
}
