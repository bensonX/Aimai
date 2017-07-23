package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.adapter.RecycleAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

public class LessonActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private View showin;
    private ViewGroup showingroup;
    private SpringView springView;
    private RecyclerView recycler;
    private RecycleAdapterLesson adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, LessonActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        setToolbar();
//        StatusBarTextUtil.StatusBarLightMode(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        showingroup = (ViewGroup) findViewById(R.id.showingroup);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        springView = (SpringView) findViewById(R.id.spring);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLesson(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(this, 10), true));
        recycler.setAdapter(adapter);
        springView.setHeader(new AliHeader(this, false));
        springView.setFooter(new AliFooter(this, false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getResults().add(new TestBean());
                        adapter.getResults().add(new TestBean());
                        adapter.getResults().add(new TestBean());
                        adapter.notifyItemRangeChanged(adapter.getItemCount() - 1 - 3, adapter.getItemCount() - 1);
                        springView.onFinishFreshAndLoad();
                    }
                }, 800);
            }
        });
    }

    private void initData() {
        showin = LoadingViewHelper.showin(showingroup, R.layout.layout_loading, showin);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.getResults().clear();
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.getResults().add(new TestBean());
                adapter.notifyDataSetChanged();
                LoadingViewHelper.showout(showingroup, showin);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        LessonDetailActivity.start(this);
    }
}
