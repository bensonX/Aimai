package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.EmployAddActivity;
import com.ins.aimai.ui.activity.EmploySearchActivity;
import com.ins.aimai.ui.activity.UserDetailActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterLearnEmploy;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan
 */
public class LearnEmployFragment extends BaseFragment implements OnRecycleItemClickListener, View.OnClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLearnEmploy adapter;

    public static Fragment newInstance(int position) {
        LearnEmployFragment fragment = new LearnEmployFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_EMPLOY_ADD) {
            netQueryUser(1);
        }else if (event.getEvent() == EventBean.EVENT_USER_ALLOCAT){
            netQueryUser(1);
        }else if (event.getEvent() == EventBean.EVENT_LESSON_ALLOCAT){
            netQueryUser(1);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
        registEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_learn_employ, container, false);
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
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        rootView.findViewById(R.id.lay_learnpeople_search).setOnClickListener(this);
        rootView.findViewById(R.id.btn_learnpeople_add).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLearnEmploy(getContext());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryUser(0);
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netQueryUser(1);
            }

            @Override
            public void onLoadmore() {
                netQueryUser(2);
            }
        });
    }

    private void initData() {
        netQueryUser(0);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        User user = adapter.getResults().get(viewHolder.getLayoutPosition());
        UserDetailActivity.start(getContext(), user);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_learnpeople_search:
                EmploySearchActivity.start(getContext());
                break;
            case R.id.btn_learnpeople_add:
                EmploySearchActivity.start(getContext());
                break;
        }
    }

    ///////////////////////////////////
    //////////////分页查询
    ///////////////////////////////////

    private int page;
    private final int PAGE_COUNT = 10;

    /**
     * type:0 首次加载 1:下拉刷新 2:上拉加载
     *
     * @param type
     */
    private void netQueryUser(final int type) {
        Map<String, Object> param = new NetParam()
                .put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "")
                .put("pageSize", PAGE_COUNT + "")
                .build();
        if (type == 0) loadingLayout.showLoadingView();
        NetApi.NI().queryUserAlloc(param).enqueue(new BaseCallback<List<User>>(new TypeToken<List<User>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<User> beans, String msg) {
                if (!StrUtil.isEmpty(beans)) {
                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
                    if (type == 0 || type == 1) {
                        adapter.getResults().clear();
                        page = 1;
                    } else {
                        page++;
                    }
                    adapter.getResults().addAll(beans);
                    adapter.notifyDataSetChanged();

                    //加载结束恢复列表
                    if (type == 0) {
                        loadingLayout.showOut();
                    } else {
                        springView.onFinishFreshAndLoad();
                    }
                } else {
                    //没有数据设置空数据页面，下拉加载不用，仅提示
                    if (type == 0 || type == 1) {
                        loadingLayout.showLackView();
                    } else {
                        springView.onFinishFreshAndLoad();
                        ToastUtil.showToastShort("没有更多的数据了");
                    }
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                //首次加载发生异常设置error页面，其余仅提示
                if (type == 0) {
                    loadingLayout.showFailView();
                } else {
                    springView.onFinishFreshAndLoad();
                }
            }
        });
    }
}
