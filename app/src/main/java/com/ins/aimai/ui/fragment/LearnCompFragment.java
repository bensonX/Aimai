package com.ins.aimai.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.StatisGov;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.activity.AddressActivity;
import com.ins.aimai.ui.activity.CompDetailActivity;
import com.ins.aimai.ui.activity.EmploySearchActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterLearnComp;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.SpannableStringUtil;
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
public class LearnCompFragment extends BaseFragment implements OnRecycleItemClickListener, View.OnClickListener {

    private int position;
    private View rootView;

    private TextView text_toolbar_title;
    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLearnComp adapter;

    private EditText edit_query;
    private TextView text_learncomp_note;

    private int cityId;

    public static Fragment newInstance(int position) {
        LearnCompFragment fragment = new LearnCompFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_SELECT_ADDRESS) {
            Address address = (Address) event.get("address");
            cityId = address.getId();
            setAddressData(address);
            initData();
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
        rootView = inflater.inflate(R.layout.fragment_learn_comp, container, false);
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
        User user = AppData.App.getUser();
        if (user != null) {
            cityId = user.getCityId();
            setToolbar(user.getCity().getMergerNameNoDot());
        }
    }

    private void initView() {
        text_toolbar_title = (TextView) rootView.findViewById(R.id.text_toolbar_title);
        loadingLayout = (LoadingLayout) rootView.findViewById(R.id.loadingLayout);
        springView = (SpringView) rootView.findViewById(R.id.spring);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        edit_query = (EditText) rootView.findViewById(R.id.edit_query);
        text_learncomp_note = (TextView) rootView.findViewById(R.id.text_learncomp_note);
        text_toolbar_title.setOnClickListener(this);
        edit_query.setHint(SpannableStringUtil.makeImageStartStr(getContext(), R.drawable.ic_home_search_edit, new Rect(0, 0, 42, 42), "1 搜索公司名称、地区"));
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLearnComp(getContext());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        springView.setHeader(new AliHeader(getContext(), false));
        springView.setFooter(new AliFooter(getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netQueryComps(1);
            }

            @Override
            public void onLoadmore() {
                netQueryComps(2);
            }
        });
        edit_query.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    netQueryComps(0);
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        netGovStatis();
        netQueryComps(0);
    }

    public void setAddressData(Address address) {
        if (address != null) {
            setToolbar(address.getMergerNameNoDot());
        }
    }

    public void setStatisData(StatisGov statis) {
        if (statis != null) {
            SpannableString span = SpannableStringUtil.create(getContext(), new String[]{
                    "共",
                    statis.getCompanyNum() + "",
                    "个公司、",
                    statis.getSafeNum() + "",
                    "位安全人员",
            }, new int[]{
                    R.color.com_text_dark_light,
                    R.color.com_blue,
                    R.color.com_text_dark_light,
                    R.color.com_blue,
                    R.color.com_text_dark_light,
            });
            text_learncomp_note.setText(span);
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        User comp = adapter.getResults().get(viewHolder.getLayoutPosition());
        CompDetailActivity.start(getContext(), comp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_toolbar_title:
                AddressActivity.start(getContext());
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
    private void netQueryComps(final int type) {
        final String searchParam = edit_query.getText().toString();
        Map<String, Object> param = new NetParam()
                .put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "")
                .put("pageSize", PAGE_COUNT + "")
                .put("searchParams", searchParam)
                .put("cityId", cityId)
                .build();
        if (type == 0) loadingLayout.showLoadingView();
        NetApi.NI().queryComps(param).enqueue(new BaseCallback<List<User>>(new TypeToken<List<User>>() {
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

    private void netGovStatis() {
        Map<String, Object> param = new NetParam()
                .put("cityId", cityId)
                .put("levelType", 3)
                .build();
        NetApi.NI().govStatis(param).enqueue(new BaseCallback<StatisGov>(StatisGov.class) {
            @Override
            public void onSuccess(int status, StatisGov statis, String msg) {
                setStatisData(statis);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
