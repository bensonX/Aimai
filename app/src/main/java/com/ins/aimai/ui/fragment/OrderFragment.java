package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Order;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.LessonDetailActivity;
import com.ins.aimai.ui.activity.VideoActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterOrder;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ins.aimai.R.id.loadingLayout;

/**
 * Created by liaoinstan
 */
public class OrderFragment extends BaseFragment implements OnRecycleItemClickListener, RecycleAdapterOrder.OnOrderBtnClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;

    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterOrder adapter;

    public static Fragment newInstance(int position) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_PAYRESULT) {
            netQueryOrder(1);
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
        rootView = inflater.inflate(R.layout.fragment_order, container, false);
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
    }

    private void initCtrl() {
        adapter = new RecycleAdapterOrder(getContext());
        adapter.setOnOrderBtnClickListener(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryOrder(0);
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netQueryOrder(1);
            }

            @Override
            public void onLoadmore() {
                netQueryOrder(2);
            }
        });
    }

    private void initData() {
        netQueryOrder(0);
    }

    @Override
    public void onDelClick(final Order order) {
        DialogSure.showDialog(getContext(), "确定要删除该订单？", new DialogSure.CallBack() {
            @Override
            public void onSure() {
                netDelOrder(order.getId());
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Order order = adapter.getResults().get(viewHolder.getLayoutPosition());
        if (order.isPay()) {
            VideoActivity.startByOrder(getActivity(), order.getId());
        } else {
            LessonDetailActivity.startByOrder(getActivity(), order.getId());
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
    private void netQueryOrder(final int type) {
        Map map = new HashMap<String, Object>() {{
            put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "");
            put("pageSize", PAGE_COUNT + "");
            if (position == 1) {
                put("payStatus", 0);
            } else if (position == 2) {
                put("payStatus", 1);
            }
        }};
        if (type == 0) loadingLayout.showLoadingView();
        NetApi.NI().queryOrder(NetParam.newInstance().put(map).build()).enqueue(new BaseCallback<List<Order>>(new TypeToken<List<Order>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Order> beans, String msg) {
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

    private void netDelOrder(int orderId) {
        Map<String, Object> param = new NetParam()
                .put("orderId", orderId)
                .build();
        AppHelper.showLoadingDialog(getActivity());
        NetApi.NI().delOrder(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort(msg);
                netQueryOrder(1);
                AppHelper.hideLoadingDialog(getActivity());
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                AppHelper.hideLoadingDialog(getActivity());
            }
        });
    }
}
