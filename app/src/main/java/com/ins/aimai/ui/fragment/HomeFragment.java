package com.ins.aimai.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Info;
import com.ins.aimai.bean.Msg;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.ToobarTansColorHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.ui.activity.MsgActivity;
import com.ins.aimai.ui.activity.WebActivity;
import com.ins.aimai.ui.activity.WebInfoActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeBanner;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeInfo;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.entity.Image;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.L;
import com.ins.common.view.BannerView;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by liaoinstan
 */
public class HomeFragment extends BaseFragment implements OnRecycleItemClickListener, BannerView.OnBannerClickListener, View.OnClickListener {

    private int position;
    private View rootView;

    private View img_msg_dot;

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private DelegateAdapter delegateAdapter;
    private RecycleAdapterHomeBanner adapterBanner;
    private RecycleAdapterHomeInfo adapterInfo;

    private NetListHelper netListHelper;

    public static Fragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_MSG_NEW) {
            img_msg_dot.setVisibility(View.VISIBLE);
        } else if (event.getEvent() == EventBean.EVENT_MSG_NEW_NONE) {
            img_msg_dot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar(false);
        toolbar.bringToFront();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        springView = (SpringView) rootView.findViewById(R.id.spring);
        img_msg_dot = rootView.findViewById(R.id.img_msg_dot);
        rootView.findViewById(R.id.btn_right).setOnClickListener(this);
    }

    private void initCtrl() {
        VirtualLayoutManager manager = new VirtualLayoutManager(getContext());
        recycler.setLayoutManager(manager);
        delegateAdapter = new DelegateAdapter(manager, true);
        recycler.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(adapterBanner = new RecycleAdapterHomeBanner(getContext(), new LinearLayoutHelper()));
        delegateAdapter.addAdapter(adapterInfo = new RecycleAdapterHomeInfo(getContext(), new LinearLayoutHelper()));
        adapterInfo.setOnItemClickListener(this);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netListHelper.netQueryList(0);
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netListHelper.netQueryList(1);
                netQueryBanner();
            }

            @Override
            public void onLoadmore() {
                netListHelper.netQueryList(2);
            }
        });
        netListHelper = new NetListHelper<Info>().init(
                loadingLayout,
                springView,
                new TypeToken<List<Info>>() {
                }.getType(),
                new NetListHelper.CallHander() {
                    @Override
                    public Call getCall(int type) {
                        Map param = netListHelper.getParam(type);
                        return NetApi.NI().queryInfo(param);
                    }
                },
                new NetListHelper.OnListLoadCallback<Info>() {
                    @Override
                    public void onFreshSuccess(int status, List<Info> beans, String msg) {
                        adapterInfo.getResults().clear();
                        adapterInfo.getResults().addAll(beans);
                        adapterInfo.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoadSuccess(int status, List<Info> beans, String msg) {
                        adapterInfo.getResults().addAll(beans);
                        adapterInfo.notifyDataSetChanged();
                    }
                });
        adapterBanner.setOnBannerClickListener(this);
        //设置toolbar的颜色渐变器及阈值回调
        ToobarTansColorHelper.getInstance().with(recycler, toolbar).onPointCallback(new ToobarTansColorHelper.OnPointListener() {
            @Override
            public void onPoint(boolean upOrDown) {
                L.e(upOrDown);
            }
        });
    }

    private void initData() {
        netListHelper.netQueryList(0);
        netQueryBanner();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                MsgActivity.start(getActivity());
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Info info = adapterInfo.getResults().get(viewHolder.getLayoutPosition() - 1);
        if (info.isLink()) {
            WebActivity.start(getActivity(), info.getTitle(), info.getLinkUrl());
        } else {
            WebInfoActivity.start(getActivity(), info);
        }
    }

    @Override
    public void onBannerClick(int position) {
        Image img = adapterBanner.getResults().get(position);
        if (img.isLink()) {
            WebActivity.start(getActivity(), img.getTitle(), img.getUrl());
        } else {
            WebInfoActivity.start(getContext(), img);
        }
    }

    private void netQueryBanner() {
        Map<String, Object> param = new NetParam().build();
        NetApi.NI().queryBanner(param).enqueue(new BaseCallback<List<Image>>(new TypeToken<List<Image>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Image> images, String msg) {
                adapterBanner.getResults().clear();
                adapterBanner.getResults().addAll(images);
                adapterBanner.notifyDataSetChanged();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
