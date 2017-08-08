package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Info;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.helper.NetListHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterHomeInfo;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class InfoActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener, View.OnClickListener {

    private LoadingLayout loadingLayout;
    private View lay_info_search;
    private TextView text_toolbar_title;
    private EditText edit_query;
    private View btn_search;
    private View btn_close;

    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterHomeInfo adapter;

    private NetListHelper netListHelper;

    public static void start(Context context) {
        Intent intent = new Intent(context, InfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        lay_info_search = findViewById(R.id.lay_info_search);
        text_toolbar_title = (TextView) findViewById(R.id.text_toolbar_title);
        edit_query = (EditText) findViewById(R.id.edit_query);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
        btn_search = findViewById(R.id.btn_search);
        btn_close = findViewById(R.id.btn_close);
        btn_search.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterHomeInfo(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(this));
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
        netListHelper = new NetListHelper<Info>().init(loadingLayout, springView,
                new TypeToken<List<Info>>() {
                }.getType(),
                new NetListHelper.CallHander() {
                    @Override
                    public Call getCall(int type) {
                        String searchParam = edit_query.getText().toString();
                        Map param = netListHelper.getParam(type);
                        param.put("searchParam", searchParam);
                        return NetApi.NI().queryInfo(param);
                    }
                },
                new NetListHelper.OnListLoadCallback<Info>() {
                    @Override
                    public void onFreshSuccess(int status, List<Info> beans, String msg) {
                        adapter.getResults().clear();
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoadSuccess(int status, List<Info> beans, String msg) {
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    }
                });
        edit_query.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    netListHelper.netQueryList(0);
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        netListHelper.netQueryList(0);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Info info = adapter.getResults().get(viewHolder.getLayoutPosition());
        WebInfoActivity.start(this, info);
//        String url = NetApi.getBaseUrl() + AppData.Url.newsInfo + "?newsId=" + info.getId();
//        WebActivity.startByLesson(this, info.getTitle(), url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                lay_info_search.setVisibility(View.VISIBLE);
                text_toolbar_title.setVisibility(View.GONE);
                btn_search.setVisibility(View.GONE);
                setToolbar(false);
                break;
            case R.id.btn_close:
                lay_info_search.setVisibility(View.GONE);
                text_toolbar_title.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.VISIBLE);
                //edit_query.setText("");
                //netListHelper.netQueryList(0);
                setToolbar(true);
                break;
        }
    }
}
