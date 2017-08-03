package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.RecycleAdapterLessonAllocat;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonAllocatActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLessonAllocat adapter;

    private int userId;

    public static void start(Context context, int userId) {
        Intent intent = new Intent(context, LessonAllocatActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_allocat);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("userId")) {
            userId = getIntent().getIntExtra("userId", 0);
        }
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
        findViewById(R.id.btn_right).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLessonAllocat(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(this));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryLessonAllocat(0);
            }
        });
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                netQueryLessonAllocat(1);
            }

            @Override
            public void onLoadmore() {
                netQueryLessonAllocat(2);
            }
        });
    }

    private void initData() {
        netQueryLessonAllocat(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                final String ids = adapter.getSelectedIds();
                String msg = AppVali.allocatLesson(ids);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    DialogSure.showDialog(this, "确定要把这些课程分配给该员工？", new DialogSure.CallBack() {
                        @Override
                        public void onSure() {
                            netLessonAllocat(ids);
                        }
                    });
                }
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
    private void netQueryLessonAllocat(final int type) {
        Map map = new HashMap<String, Object>() {{
            put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "");
            put("pageSize", PAGE_COUNT + "");
            put("userId", userId);
        }};
        if (type == 0) loadingLayout.showLoadingView();
        NetApi.NI().queryLessonAllocatList(NetParam.newInstance().put(map).build()).enqueue(new BaseCallback<List<Lesson>>(new TypeToken<List<Lesson>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Lesson> beans, String msg) {
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

    private void netLessonAllocat(final String ids) {
        Map<String, Object> param = new NetParam()
                .put("userId", userId)
                .put("orderIds", ids)
                .put("number", 1)
                .build();
        showLoadingDialog();
        NetApi.NI().lessonAllocat(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort(msg, true);
                hideLoadingDialog();
                //post分配成功消息，附带分配人数（课程数）
                EventBean eventBean = new EventBean(EventBean.EVENT_LESSON_ALLOCAT);
                eventBean.put("count", ids.split(",").length);
                EventBus.getDefault().post(eventBean);
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
