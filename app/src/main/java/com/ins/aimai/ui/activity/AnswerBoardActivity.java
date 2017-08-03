package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.aimai.R;
import com.ins.aimai.bean.ExamResultPojo;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetExamHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterAnswerBoard;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.DialogTimeEnd;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.interfaces.OnRecycleItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

public class AnswerBoardActivity extends BaseAppCompatActivity implements OnRecycleItemClickListener, View.OnClickListener {

    private RecyclerView recycler;
    private RecycleAdapterAnswerBoard adapter;
    private DialogTimeEnd dialogTimeEnd;

    private int type;
    private ArrayList<QuestionBean> questions;
    private int paperId;
    private int orderId;

    public static void start(Context context, ArrayList<QuestionBean> questions, int paperId, int orderId, int type) {
        Intent intent = new Intent(context, AnswerBoardActivity.class);
        intent.putExtra("questions", questions);
        intent.putExtra("paperId", paperId);
        intent.putExtra("orderId", orderId);
        intent.putExtra("type", type);
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
    }

    private void initBase() {
        if (getIntent().hasExtra("questions")) {
            questions = (ArrayList<QuestionBean>) getIntent().getSerializableExtra("questions");
        }
        if (getIntent().hasExtra("paperId")) {
            paperId = getIntent().getIntExtra("paperId", 0);
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
        }
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        dialogTimeEnd = new DialogTimeEnd(this);
    }

    private void initView() {
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
        adapter.getResults().clear();
        adapter.getResults().addAll(questions);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        EventBean eventBean = new EventBean(EventBean.EVENT_EXAMBOARD_SELECT);
        eventBean.put("position", viewHolder.getLayoutPosition());
        EventBus.getDefault().post(eventBean);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                NetExamHelper.getInstance().submitExam(paperId, orderId, 0, questions, new NetExamHelper.OnExamSubmitCallback() {
                    @Override
                    public void onSuccess(ExamResultPojo examResultPojo) {
                        EventBus.getDefault().post(new EventBean(EventBean.EVENT_EXAM_SUBMITED));
                        ExamResultActivity.start(AnswerBoardActivity.this, paperId, orderId, type);
                        finish();
                    }
                });
                break;
        }
    }
}
