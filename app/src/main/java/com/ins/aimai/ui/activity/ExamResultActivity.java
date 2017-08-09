package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.ExamResultPojo;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetExamHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterExamResult;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//type 0：练习题 1：模拟题 2：考试题
public class ExamResultActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener, View.OnClickListener {

    private RecyclerView recycler;
    private RecycleAdapterExamResult adapter;

    private View lay_examresult_gradeboard;
    private TextView text_examresult_grade;
    private TextView text_examresult_remind;
    private TextView text_examresult_note;

    private int type;
    private int paperId;
    private int orderId;
    private ExamResultPojo examResultPojo;

    public static void start(Context context, int paperId, int orderId, int type) {
        Intent intent = new Intent(context, ExamResultActivity.class);
        intent.putExtra("paperId", paperId);
        intent.putExtra("orderId", orderId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        setToolbar();
//        StatusBarTextUtil.StatusBarLightMode(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (getIntent().hasExtra("paperId")) {
            paperId = getIntent().getIntExtra("paperId", 0);
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
        }
        if (getIntent().hasExtra("examResultPojo")) {
            examResultPojo = (ExamResultPojo) getIntent().getSerializableExtra("examResultPojo");
        }
    }

    private void initView() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        lay_examresult_gradeboard = findViewById(R.id.lay_examresult_gradeboard);
        text_examresult_grade = (TextView) findViewById(R.id.text_examresult_grade);
        text_examresult_remind = (TextView) findViewById(R.id.text_examresult_remind);
        text_examresult_note = (TextView) findViewById(R.id.text_examresult_note);
        findViewById(R.id.btn_go_error).setOnClickListener(this);
        findViewById(R.id.btn_go_all).setOnClickListener(this);

        //练习题不显示分数面板
        lay_examresult_gradeboard.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterExamResult(this);
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    private void initData() {
        netQueryPaperRecord();
    }

    private void setData(ExamResultPojo examResultPojo) {
        if (examResultPojo != null) {
            //设置基础数据
            text_examresult_grade.setText(examResultPojo.getResultScore() + "");
            if (examResultPojo.isPass()) {
                text_examresult_grade.setTextColor(ContextCompat.getColor(this, R.color.am_blue));
                text_examresult_remind.setText("恭喜您！通过本次考核。");
                text_examresult_note.setVisibility(View.GONE);
            } else {
                text_examresult_grade.setTextColor(ContextCompat.getColor(this, R.color.com_text_dark));
                text_examresult_remind.setText("抱歉！您没有通过本次考核。");
                //正式考试才有note提示
                if (type != 2) {
                    text_examresult_note.setVisibility(View.GONE);
                } else {
                    text_examresult_note.setVisibility(View.VISIBLE);
                    text_examresult_note.setText("您还有" + "一" + "次重新考核的机会");
                }
            }
            //设置答题数据
            adapter.getResults().clear();
            adapter.getResults().addAll(examResultPojo.getJsonArray());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_error:
                QuestionAnalysisActivity.startError(this, adapter.getErrorIdsList());
                break;
            case R.id.btn_go_all:
                QuestionAnalysisActivity.startAll(this, adapter.getAllIdsList());
                break;
        }
    }

    public void netQueryPaperRecord() {
        Map<String, Object> param = new NetParam()
                .put("paperId", paperId)
                .put("orderId", orderId)
                .build();
        showLoadingDialog();
        NetApi.NI().queryPaperRecord(param).enqueue(new BaseCallback<ExamResultPojo>(ExamResultPojo.class) {
            @Override
            public void onSuccess(int status, ExamResultPojo examResultPojo, String msg) {
                ExamResultActivity.this.examResultPojo = examResultPojo;
                hideLoadingDialog();
                setData(ExamResultActivity.this.examResultPojo);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
