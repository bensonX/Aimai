package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterLearnUser;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

//type 0:已观看，1 围观看 2 以考核
public class LearnUserActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLearnUser adapter;

    private NetListHelper netListHelper;

    private int type;
    private int count;

    public static void startWatch(Context context, int watchCount) {
        start(context, watchCount, 0);
    }

    public static void startUnWatch(Context context, int unWatchCount) {
        start(context, unWatchCount, 1);
    }

    public static void startExamed(Context context, int examedCount) {
        start(context, examedCount, 2);
    }

    private static void start(Context context, int count, int type) {
        Intent intent = new Intent(context, LearnUserActivity.class);
        intent.putExtra("count", count);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_user);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (getIntent().hasExtra("count")) {
            count = getIntent().getIntExtra("count", 0);
        }
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLearnUser(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netListHelper.netQueryList(0);
            }
        });
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netListHelper.netQueryList(1);
            }

            @Override
            public void onLoadmore() {
                netListHelper.netQueryList(2);
            }
        });
        netListHelper = new NetListHelper<User>().init(loadingLayout, springView,
                new TypeToken<List<User>>() {
                }.getType(),
                new NetListHelper.CallHander() {
                    @Override
                    public Call getCall(int type) {
                        Map param = netListHelper.getParam(type);
                        //param.put("type", 1);
                        return NetApi.NI().queryLearnUser(param);
                    }
                },
                new NetListHelper.OnListLoadCallback<User>() {
                    @Override
                    public void onFreshSuccess(int status, List<User> beans, String msg) {
                        adapter.getResults().clear();
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoadSuccess(int status, List<User> beans, String msg) {
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void initData() {
        netListHelper.netQueryList(0);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
    }
}
