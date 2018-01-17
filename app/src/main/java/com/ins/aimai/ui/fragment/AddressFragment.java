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
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetAddressHelper;
import com.ins.aimai.ui.activity.AddressActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterAddress;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
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
        if (levelType == 1 && address.getId() == 100000 && isHasAll()) {
            //如果政府用户选择了“中国”，则直接返回
            ((AddressActivity) getActivity()).postAddressAndFinish(address);
        } else {
            EventBus.getDefault().post(address);
            activity.next(address);
        }
    }

    private void netGetAddress(int id) {
        NetAddressHelper.getInstance().netGetAddress(id, levelType, new NetAddressHelper.AddressCallback() {
            @Override
            public void onSuccess(List<Address> addressList) {
                adapter.getResults().clear();
                adapter.getResults().addAll(addressList);
                //如果是政府用户那么在选择省份的时候第一项增加一个“全国”
                if (levelType == 1 && isHasAll()) {
                    Address address = new Address();
                    address.setId(100000);
                    address.setAddress("全国");
                    address.setName("全国");
                    address.setShortName("全国");
                    address.setMergerName("全国");
                    adapter.getResults().add(0, address);
                }
                adapter.notifyDataSetChanged();
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort(msg);
                springView.onFinishFreshAndLoad();
            }
        });
    }

    //判断用户是否可以选择全国
    private boolean isHasAll() {
        return getActivity() instanceof AddressActivity && ((AddressActivity) getActivity()).isHasAll();
    }
}
