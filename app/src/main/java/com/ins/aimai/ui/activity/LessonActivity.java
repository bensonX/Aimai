package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.RecycleAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private LoadingLayout loadingLayout;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLesson adapter;

    //类别id，如果为-1代表推荐列表
    private int cateId;
    private String cateTitle;

    public static void start(Context context, int cateId, String cateTitle) {
        Intent intent = new Intent(context, LessonActivity.class);
        intent.putExtra("cateId", cateId);
        intent.putExtra("cateTitle", cateTitle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("cateId")) {
            cateId = getIntent().getIntExtra("cateId", 0);
        }
        if (getIntent().hasExtra("cateTitle")) {
            cateTitle = getIntent().getStringExtra("cateTitle");
            setToolbar(cateTitle);
        }
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLesson(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(this, 10), true));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        netQueryTradeByCate(1);
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.getResults().add(new TestBean());
//                        adapter.getResults().add(new TestBean());
//                        adapter.getResults().add(new TestBean());
//                        adapter.notifyItemRangeChanged(adapter.getItemCount() - 1 - 3, adapter.getItemCount() - 1);
//                        springView.onFinishFreshAndLoad();
                        netQueryTradeByCate(2);
                    }
                }, 800);
            }
        });
    }

    private void initData() {
//        showin = LoadingViewHelper.showin(showingroup, R.layout.layout_loading, showin);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                adapter.getResults().clear();
//                adapter.getResults().add(new Lesson());
//                adapter.notifyDataSetChanged();
//                LoadingViewHelper.showout(showingroup, showin);
//            }
//        }, 1000);
        netQueryTradeByCate(0);
    }

    private void freshLoadmore(List<Lesson> beans) {
        adapter.notifyItemRangeChanged(adapter.getItemCount() - 1 - beans.size(), adapter.getItemCount() - beans.size());
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Lesson lesson = adapter.getResults().get(viewHolder.getLayoutPosition());
        LessonDetailActivity.start(this, lesson.getId());
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
    private void netQueryTradeByCate(final int type) {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("pageNO", type == 0 || type == 1 ? "1" : page + 1 + "");
            put("pageSize", PAGE_COUNT + "");
            if (cateId != -1) {
                put("curriculumStageId", cateId);
            } else {
                put("isRecommend", 1);
            }
        }};
        if (type == 0) loadingLayout.showLoadingView();
        NetApi.NI().queryLessonByCate(NetParam.newInstance().put(map).build()).enqueue(new BaseCallback<List<Lesson>>(new TypeToken<List<Lesson>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Lesson> beans, String msg) {
                if (!StrUtil.isEmpty(beans)) {
                    //下拉加载和首次加载要清除原有数据并把页码置为1，上拉加载不断累加页码
                    if (type == 0 || type == 1) {
                        page = 1;
                        adapter.getResults().clear();
                        adapter.getResults().addAll(beans);
                        adapter.notifyDataSetChanged();
                    } else {
                        page++;
                        adapter.getResults().addAll(beans);
                        freshLoadmore(beans);
                    }

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
