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
import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.EventBean;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.AddressActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterAddress;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan
 */
public class AddressFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private LoadingLayout loadingLayout;

    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterAddress adapter;

    private AddressActivity activity;

    private int levelType;
    private Address address;

    public static Fragment newInstance(int position) {
        AddressFragment fragment = new AddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressEvent(Address address) {
        switch (levelType) {
            case 2:
                //选择城市
                if (address.getLevelType() == 1) {
                    this.address = address;
                    initData();
                }
                break;
            case 3:
                //选择区县
                if (address.getLevelType() == 2) {
                    this.address = address;
                    initData();
                }
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
        this.levelType = position + 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_address, container, false);
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
        activity = (AddressActivity) getActivity();
    }

    private void initView() {
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterAddress(getContext());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToastShort("没有更多数据了");
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
    }

    private void initData() {
        if (levelType == 1) {
            netGetAddress(0);
        } else {
            if (address != null) netGetAddress(address.getId());
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Address address = adapter.getResults().get(viewHolder.getLayoutPosition());
        EventBus.getDefault().post(address);
        activity.next(address);
    }

    private void netGetAddress(int id) {
        NetParam netParam = new NetParam();
        netParam.put("levelType", levelType);
        if (id != 0) netParam.put("cityId", id);
        Map<String, Object> param = netParam.build();
        loadingLayout.showLoadingView();
        NetApi.NI().queryCity(param).enqueue(new BaseCallback<List<Address>>(new TypeToken<List<Address>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Address> beans, String msg) {
                if (StrUtil.isEmpty(beans)) {
                    loadingLayout.showLackView();
                } else {
                    adapter.getResults().clear();
                    adapter.getResults().addAll(beans);
                    adapter.notifyDataSetChanged();
                    loadingLayout.showOut();
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                loadingLayout.showFailView();
            }
        });
    }
}
