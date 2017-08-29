package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

//flag 0:查看已观看的人数 1:查看已考核人数
public class LearnUserActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private LoadingLayout loadingLayout;
    private TextView text_learnuser_count;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLearnUser adapter;

    private NetListHelper netListHelper;

    private int flag;
    private int count;
    private int orderId;

    public static void startWatch(Context context, Lesson lesson) {
        start(context, lesson.getOrderId(), lesson.getWatchNum(), 0);
    }

    public static void startExamed(Context context, Lesson lesson) {
        start(context, lesson.getOrderId(), lesson.getFinishExamine(), 1);
    }

    private static void start(Context context, int orderId, int count, int flag) {
        Intent intent = new Intent(context, LearnUserActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("count", count);
        intent.putExtra("flag", flag);
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
        if (getIntent().hasExtra("flag")) {
            flag = getIntent().getIntExtra("flag", 0);
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
        }
        if (getIntent().hasExtra("count")) {
            count = getIntent().getIntExtra("count", 0);
        }
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        text_learnuser_count = (TextView) findViewById(R.id.text_learnuser_count);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);

        text_learnuser_count.setText("共" + count + "位同事" + (flag == 0 ? "观看" : "考核"));
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLearnUser(this, flag);
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
                        param.put("orderId", orderId);
                        param.put("flag", flag);
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
