package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.ins.aimai.R;
import com.ins.aimai.bean.LessonHomePojo;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterLessonCate;
import com.ins.aimai.ui.adapter.RecycleAdapterLessonTasteBanner;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.Map;

/**
 * Created by liaoinstan
 */
public class LessonFragment extends BaseFragment {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private DelegateAdapter delegateAdapter;
    private RecycleAdapterLessonTasteBanner adapterTasteBanner;
    private RecycleAdapterLessonCate adapterCate;

    public static Fragment newInstance(int position) {
        LessonFragment fragment = new LessonFragment();
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
        rootView = inflater.inflate(R.layout.fragment_lesson, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        delegateAdapter.addAdapter(adapterTasteBanner = new RecycleAdapterLessonTasteBanner(getContext(), new LinearLayoutHelper()));
        delegateAdapter.addAdapter(adapterCate = new RecycleAdapterLessonCate(getContext(), new LinearLayoutHelper()));
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryLessonHome();
            }
        });
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapterCate.getResults().add(new TestBean());
                        adapterCate.getResults().add(new TestBean());
                        adapterCate.notifyDataSetChanged();
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
        adapterTasteBanner.setOnItemClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                VideoActivity.start(getContext());
            }
        });
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                adapterCate.getResults().clear();
//                adapterCate.getResults().add(new TestBean());
//                adapterCate.getResults().add(new TestBean());
//                adapterCate.getResults().add(new TestBean());
//                adapterCate.getResults().add(new TestBean());
//                adapterCate.getResults().add(new TestBean());
//                adapterCate.getResults().add(new TestBean());
//                adapterCate.getResults().add(new TestBean());
//                adapterCate.notifyDataSetChanged();
                adapterTasteBanner.getResults().clear();
                adapterTasteBanner.getResults().add(new TestBean());
                adapterTasteBanner.getResults().add(new TestBean());
                adapterTasteBanner.getResults().add(new TestBean());
                adapterTasteBanner.getResults().add(new TestBean());
                adapterTasteBanner.getResults().add(new TestBean());
                adapterTasteBanner.getResults().add(new TestBean());
                adapterTasteBanner.notifyDataSetChanged();
            }
        }, 1000);
        netQueryLessonHome();
    }

    ///////////////////////////////////
    //////////////分页查询
    ///////////////////////////////////

    private void netQueryLessonHome() {
        Map<String, Object> param = new NetParam()
                .put("pageNO", 1)
                .put("pageSize", 2)
                .build();
        loadingLayout.showLoadingView();
        NetApi.NI().queryLessonHome(param).enqueue(new BaseCallback<LessonHomePojo>(LessonHomePojo.class) {
            @Override
            public void onSuccess(int status, LessonHomePojo bean, String msg) {
                loadingLayout.showOut();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                loadingLayout.showFailView();
            }
        });
    }
}
