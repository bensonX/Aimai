package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Examination;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.RecycleAdapterQuestionAnalysis;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//type:0：错题集 1 解析
public class QuestionAnalysisActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener {

    private LoadingLayout loadingLayout;
    private RecyclerView recycler;
    private RecycleAdapterQuestionAnalysis adapter;

    private int type;
    private int orderId;
    private int courseWareId;
    private String lessonName;
    private String wareName;
    private String title;
    private List<Integer> ids;
    private ArrayList<QuestionBean> questions;

    public static void startErrorWithCate(Context context, int orderId, int courseWareId, String lessonName, String wareName) {
        Intent intent = new Intent(context, QuestionAnalysisActivity.class);
        intent.putExtra("title", "错题解析");
        intent.putExtra("orderId", orderId);
        intent.putExtra("courseWareId", courseWareId);
        intent.putExtra("lessonName", lessonName);
        intent.putExtra("wareName", wareName);
        context.startActivity(intent);
    }

    public static void startError(Context context, ArrayList<Integer> ids) {
        if (StrUtil.isEmpty(ids)){
            ToastUtil.showToastShort("没有错题");
            return;
        }
        start(context, ids, "错题解析");
    }

    public static void startAll(Context context, ArrayList<Integer> ids) {
        if (StrUtil.isEmpty(ids)){
            ToastUtil.showToastShort("没有题目");
            return;
        }
        start(context, ids, "全部解析");
    }

    private static void start(Context context, ArrayList<Integer> ids, String title) {
        Intent intent = new Intent(context, QuestionAnalysisActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("ids", ids);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_analysis);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
            setToolbar(title);
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
            type = 0;
        }
        if (getIntent().hasExtra("courseWareId")) {
            courseWareId = getIntent().getIntExtra("courseWareId", 0);
            type = 0;
        }
        if (getIntent().hasExtra("lessonName")) {
            lessonName = getIntent().getStringExtra("lessonName");
        }
        if (getIntent().hasExtra("wareName")) {
            wareName = getIntent().getStringExtra("wareName");
        }
        if (getIntent().hasExtra("ids")) {
            ids = (ArrayList<Integer>) getIntent().getSerializableExtra("ids");
            type = 1;
        }
    }

    private void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterQuestionAnalysis(this);
        if (type == 0) adapter.setTitle(lessonName, wareName);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.addItemDecoration(new ItemDecorationDivider(this));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netQueryQuestionAnalysis();
            }
        });

    }

    private void initData() {
        if (type == 0) {
            netQueryQuestionAnalysisByPage();
        } else {
            netQueryQuestionAnalysis();
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
    }

    private void netQueryQuestionAnalysis() {
        Map<String, Object> param = new NetParam()
                .put("ids", new Gson().toJson(ids))
                .build();
        showLoadingDialog();
        NetApi.NI().queryQuestionAnalysis(param).enqueue(new BaseCallback<List<Examination>>(new TypeToken<List<Examination>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Examination> examinations, String msg) {
                questions = (ArrayList<QuestionBean>) AppHelper.Exam.transExamination2QuestionBeans(examinations);
                adapter.getResults().clear();
                adapter.getResults().addAll(questions);
                adapter.notifyDataSetChanged();
                hideLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                hideLoadingDialog();
                ToastUtil.showToastShort(msg);
            }
        });
    }

    private void netQueryQuestionAnalysisByPage() {
        Map<String, Object> param = new NetParam()
                .put("orderId", orderId)
                .put("courseWareId", courseWareId)
                .put("pageNO", 1)
                .put("pageSize", 1000)
                .build();
        showLoadingDialog();
        NetApi.NI().queryQuestionAnalysisByPage(param).enqueue(new BaseCallback<List<Examination>>(new TypeToken<List<Examination>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Examination> examinations, String msg) {
                questions = (ArrayList<QuestionBean>) AppHelper.Exam.transExamination2QuestionBeans(examinations);
                adapter.getResults().clear();
                adapter.getResults().addAll(questions);
                adapter.notifyDataSetChanged();
                hideLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                hideLoadingDialog();
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
