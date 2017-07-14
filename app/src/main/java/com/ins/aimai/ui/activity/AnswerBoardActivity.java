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
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.RecycleAdapterAnswerBoard;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.DialogTimeEnd;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;

public class AnswerBoardActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener,View.OnClickListener {

    private View showin;
    private ViewGroup showingroup;
    private RecyclerView recycler;
    private RecycleAdapterAnswerBoard adapter;
    private DialogTimeEnd dialogTimeEnd;

    public static void start(Context context) {
        Intent intent = new Intent(context, AnswerBoardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_board);
        setToolbar();
//        StatusBarTextUtil.StatusBarLightMode(this);
        initBase();
        initView();
        initCtrl();
        initData();
        dialogTimeEnd.show();
    }

    private void initBase() {
        dialogTimeEnd = new DialogTimeEnd(this);
    }

    private void initView() {
        showingroup = (ViewGroup) findViewById(R.id.showingroup);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        findViewById(R.id.btn_go).setOnClickListener(this);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterAnswerBoard(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    private void initData() {
        showin = LoadingViewHelper.showin(showingroup, R.layout.layout_loading, showin);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.getResults().clear();
                for (int i = 0; i < 100; i++) {
                    adapter.getResults().add(new TestBean());
                }
                adapter.notifyDataSetChanged();
                LoadingViewHelper.showout(showingroup, showin);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        LessonDetailActivity.start(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_go:
                TestResultActivity.start(this);
                break;
        }
    }
}
