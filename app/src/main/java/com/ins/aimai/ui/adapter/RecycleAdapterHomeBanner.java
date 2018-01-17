package com.ins.aimai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.Address;
import com.ins.aimai.common.AppData;
import com.ins.aimai.interfaces.OnSimpleItemSelectedListener;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.helper.NetAddressHelper;
import com.ins.aimai.ui.activity.InfoActivity;
import com.ins.aimai.ui.activity.WebActivity;
import com.ins.aimai.utils.ToastUtil;
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

        if (isSpan) {
            holder.lay_home_sort.setVisibility(View.GONE);
            holder.text_home_span.setText("展开");
            holder.text_home_span.setSelected(true);
        } else {
            holder.lay_home_sort.setVisibility(View.VISIBLE);
            holder.text_home_span.setText("收起");
            holder.text_home_span.setSelected(false);
        }

        netGetAddress(0, 1, holder);
        holder.text_address_whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(holder, v);
            }
        });
        holder.spinner_province.setOnItemSelectedListener(new OnSimpleItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Address address = holder.adapterProvince.getResults().get(position);
                netGetAddress(address.getId(), 2, holder);
                setSelection(holder, holder.spinner_province);
                if (onFilterSelectListenner != null)
                    onFilterSelectListenner.onSpinnerSelect(1, address);
            }
        });
        holder.spinner_city.setOnItemSelectedListener(new OnSimpleItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Address address = holder.adapterCity.getResults().get(position);
                setSelection(holder, holder.spinner_city);
                if (onFilterSelectListenner != null)
                    onFilterSelectListenner.onSpinnerSelect(2, address);
            }
        });
        holder.radiogroup_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String typeStr = (String) group.findViewById(checkedId).getTag();
                int type = Integer.parseInt(typeStr);
                if (onFilterSelectListenner != null) onFilterSelectListenner.onTypeSelect(type);
            }
        });
        holder.radiogroup_sort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String sortStr = (String) group.findViewById(checkedId).getTag();
                int sort = Integer.parseInt(sortStr);
                if (onFilterSelectListenner != null) onFilterSelectListenner.onSortSelect(sort);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private void setSelection(Holder holder, View view) {
        switch (view.getId()) {
            case R.id.text_address_whole:
                holder.text_address_whole.setSelected(true);
                holder.spinner_province.setSelected(false);
                holder.spinner_city.setSelected(false);
                break;
            case R.id.spinner_province:
                holder.text_address_whole.setSelected(false);
                holder.spinner_province.setSelected(true);
                holder.spinner_city.setSelected(false);
                break;
            case R.id.spinner_city:
                holder.text_address_whole.setSelected(false);
                holder.spinner_province.setSelected(false);
                holder.spinner_city.setSelected(true);
                break;
        }
    }

    public class Holder extends RecyclerView.ViewHolder {

        private BannerView banner;
        private TextView text_home_span;
        private View lay_home_about;
        private View lay_home_quali;
        private View lay_home_station;
        private View btn_home_more;
        private View lay_home_sort;
        private TextView text_address_whole;
        private Spinner spinner_province;
        private Spinner spinner_city;
        private RadioGroup radiogroup_type;
        private RadioGroup radiogroup_sort;

        private ListAdapterAddress adapterProvince;
        private ListAdapterAddress adapterCity;

        public Holder(View itemView) {
            super(itemView);
            lay_home_about = itemView.findViewById(R.id.lay_home_about);
            lay_home_quali = itemView.findViewById(R.id.lay_home_quali);
            lay_home_station = itemView.findViewById(R.id.lay_home_station);
            btn_home_more = itemView.findViewById(R.id.btn_home_more);
            lay_home_sort = itemView.findViewById(R.id.lay_home_sort);
            text_home_span = (TextView) itemView.findViewById(R.id.text_home_span);
            text_address_whole = (TextView) itemView.findViewById(R.id.text_address_whole);
            spinner_province = (Spinner) itemView.findViewById(R.id.spinner_province);
            spinner_city = (Spinner) itemView.findViewById(R.id.spinner_city);
            radiogroup_type = (RadioGroup) itemView.findViewById(R.id.radiogroup_type);
            radiogroup_sort = (RadioGroup) itemView.findViewById(R.id.radiogroup_sort);
            banner = (BannerView) itemView.findViewById(R.id.banner);

            banner.showTitle(false);
            banner.setOnLoadImgListener(new BannerView.OnLoadImgListener() {
                @Override
                public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                    GlideUtil.loadImg(imageView, defaultSrc, imgurl);
                }
            });
            adapterProvince = new ListAdapterAddress(context);
            adapterCity = new ListAdapterAddress(context);
            spinner_province.setAdapter(adapterProvince);
            spinner_city.setAdapter(adapterCity);
            //FIXME:在设置事件之前把选择定位到第一条，解决事件默认触发的BUG
            spinner_province.setSelection(0, true);
            spinner_city.setSelection(0, true);
        }
    }

    private BannerView.OnBannerClickListener onBannerClickListener;

    public void setOnBannerClickListener(BannerView.OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    private OnFilterSelectListenner onFilterSelectListenner;

    public void setOnFilterSelectListenner(OnFilterSelectListenner onFilterSelectListenner) {
        this.onFilterSelectListenner = onFilterSelectListenner;
    }

    public interface OnFilterSelectListenner {
        void onSpinnerSelect(int levelType, Address address);

        void onTypeSelect(int type);

        void onSortSelect(int sort);
    }

    private void netGetAddress(int id, final int levelType, final Holder holder) {
        NetAddressHelper.getInstance().netGetAddress(id, levelType, new NetAddressHelper.AddressCallback() {
            @Override
            public void onSuccess(List<Address> addressList) {
                addressList.add(0, new Address(0, "全部"));
                switch (levelType) {
                    case 1:
                        holder.adapterProvince.getResults().clear();
                        holder.adapterProvince.getResults().addAll(addressList);
                        holder.adapterProvince.notifyDataSetChanged();
                        break;
                    case 2:
                        holder.adapterCity.getResults().clear();
                        holder.adapterCity.getResults().addAll(addressList);
                        holder.adapterCity.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort("获取地址失败：" + msg);
            }
        });
    }
}
