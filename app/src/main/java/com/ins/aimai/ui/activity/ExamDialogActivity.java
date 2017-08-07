package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.ExamResultPojo;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.common.ExamCountDownTimer;
import com.ins.aimai.net.helper.NetExamHelper;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.fragment.PayDialogCountFragment;
import com.ins.aimai.ui.fragment.PayDialogWayFragment;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.ViewPagerUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ExamDialogActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private TextView text_examdialog_title;
    private TextView text_examdialog_time;
    private TextView text_examdialog_note;
    private TextView btn_go;

    private int type;
    private int paperId;
    private int orderId;
    private List<QuestionBean> questions;

    public static void start(Context context, ArrayList<QuestionBean> questions, int paperId, int orderId, int type) {
        Intent intent = new Intent(context, ExamDialogActivity.class);
        intent.putExtra("questions", questions);
        intent.putExtra("paperId", paperId);
        intent.putExtra("orderId", orderId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_EXAM_SUBMITED) {
            finish();
        } else if (event.getEvent() == EventBean.EVENT_EXAM_TIME) {
            int time = (int) event.get("time");
            setTimeNote(time);
        } else if (event.getEvent() == EventBean.EVENT_EXAM_TIMEOUT) {
            netCommit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examdialog);
        registEventBus();

        //设置从下方弹起，铺满宽度
        Window win = this.getWindow();
        win.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);

        initBase();
        initView();
        initData();
        initCtrl();
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
    }

    private void initView() {
        text_examdialog_title = (TextView) findViewById(R.id.text_examdialog_title);
        text_examdialog_time = (TextView) findViewById(R.id.text_examdialog_time);
        text_examdialog_note = (TextView) findViewById(R.id.text_examdialog_note);
        btn_go = (TextView) findViewById(R.id.btn_go);
        btn_go.setOnClickListener(this);

        FontUtils.boldText(text_examdialog_title);
        FontUtils.boldText(text_examdialog_time);
    }

    private void initData() {
    }

    private void initCtrl() {
    }

    private void setTimeNote(int time) {
        text_examdialog_time.setText(time + "");
        text_examdialog_note.setText(time + "秒之后我们将自动为您提交答案");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                netCommit();
                break;
        }
    }

    private void netCommit() {
        NetExamHelper.getInstance().submitExam(paperId, orderId, ExamCountDownTimer.useTime, questions, new NetExamHelper.OnExamSubmitCallback() {
            @Override
            public void onSuccess(ExamResultPojo examResultPojo) {
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_EXAM_SUBMITED));
                ExamResultActivity.start(ExamDialogActivity.this, paperId, orderId, type);
                finish();
            }
        });
    }
}
