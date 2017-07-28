package com.ins.aimai.ui.fragment;

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
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.ui.activity.WebActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeBanner;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeInfo;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.entity.Image;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.BannerView;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan
 */
public class HomeFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setToolbar();
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
                netListHelper.netQueryInfo(0);
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netListHelper.netQueryInfo(1);
                netQueryBanner();
            }

            @Override
            public void onLoadmore() {
                netListHelper.netQueryInfo(2);
            }
        });
        netListHelper = new NetListHelper<Info>().init(loadingLayout, springView, new TypeToken<List<Info>>() {
        }.getType(), new NetListHelper.OnListLoadCallback<Info>() {
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
        adapterBanner.setOnBannerClickListener(new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                ToastUtil.showToastShort(position + "");
            }
        });
    }

    private void initData() {
        netListHelper.netQueryInfo(0);
        netQueryBanner();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        WebActivity.start(getContext(), "http://cn.bing.com");
//        WebActivity.start(getContext(),"http://192.168.1.206:8080/api/page/courseVideo");
    }

    private List<TestBean> getInitResults(String name) {
        ArrayList<TestBean> results = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            results.add(new TestBean(name + i));
        }
        return results;
    }

    ///////////////////////////////////
    //////////////分页查询
    ///////////////////////////////////

    //    private int page;
//    private final int PAGE_COUNT = 10;
//
//    /**
//     * type:0 首次加载 1:下拉刷新 2:上拉加载
//     *
//     * @param type
//     */
//    private void netQueryInfo(final int type) {
//        Map<String, Object> param = new NetParam()
//                .put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "")
//                .put("pageSize", PAGE_COUNT + "")
//                .build();
//        if (type == 0) loadingLayout.showLoadingView();
//        NetApi.NI().queryInfo(param).enqueue(new BaseCallback<List<Info>>(new TypeToken<List<Info>>() {
//        }.getType()) {
//            @Override
//            public void onSuccess(int status, List<Info> beans, String msg) {
//                if (!StrUtil.isEmpty(beans)) {
//                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
//                    if (type == 0 || type == 1) {
//                        adapterInfo.getResults().clear();
//                        page = 1;
//                    } else {
//                        page++;
//                    }
//                    adapterInfo.getResults().addAll(beans);
//                    adapterInfo.notifyDataSetChanged();
//
//                    //加载结束恢复列表
//                    if (type == 0) {
//                        loadingLayout.showOut();
//                    } else {
//                        springView.onFinishFreshAndLoad();
//                    }
//                } else {
//                    //没有数据设置空数据页面，下拉加载不用，仅提示
//                    if (type == 0 || type == 1) {
//                        loadingLayout.showLackView();
//                    } else {
//                        springView.onFinishFreshAndLoad();
//                        ToastUtil.showToastShort("没有更多的数据了");
//                    }
//                }
//            }
//
//            @Override
//            public void onError(int status, String msg) {
//                ToastUtil.showToastShort(msg);
//                //首次加载发生异常设置error页面，其余仅提示
//                if (type == 0) {
//                    loadingLayout.showFailView();
//                } else {
//                    springView.onFinishFreshAndLoad();
//                }
//            }
//        });
//    }

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
