package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Exam;
import com.ins.aimai.bean.Examination;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.PagerAdapterExam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.PopTextSize;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.ViewPagerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//type 0：练习题 1：模拟题 2：考试题
public class ExamActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ViewPager pager;
    private PagerAdapterExam adapterPager;
    private View btn_right_textsize;
    private View btn_last;
    private View btn_next;
    private TextView text_exam_type;
    private TextView text_exam_num;
    private TextView text_exam_count;
    private PopTextSize popTextSize;

    private ArrayList<QuestionBean> questions;
    private int type;
    private int paperId;
    private int orderId;

    public List<QuestionBean> getQuestions() {
        return questions;
    }

    public static void startPractice(Context context, Exam exam) {
        start(context, 0, exam.getPaperId(), exam.getOrderId());
    }

    public static void startMoldel(Context context, int paperId, int orderId) {
        start(context, 1, paperId, orderId);
    }

    public static void startOfficial(Context context, int paperId, int orderId) {
        start(context, 2, paperId, orderId);
    }

    private static void start(Context context, int type, int paperId, int orderId) {
        Intent intent = new Intent(context, ExamActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("paperId", paperId);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_EXAMBOARD_SELECT) {
            int position = (int) event.get("position");
            ViewPagerUtil.goPosition(pager, position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        StatusBarTextUtil.setWindowStatusBarColor(this, R.color.am_bk);
        setToolbar(false);
        registEventBus();
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
        popTextSize = new PopTextSize(this);
        popTextSize.setNeedanim(false);
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        btn_right_textsize = findViewById(R.id.btn_right_textsize);
        btn_last = findViewById(R.id.btn_last);
        btn_next = findViewById(R.id.btn_next);
        text_exam_type = (TextView) findViewById(R.id.text_exam_type);
        text_exam_num = (TextView) findViewById(R.id.text_exam_num);
        text_exam_count = (TextView) findViewById(R.id.text_exam_count);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right_favo).setOnClickListener(this);
        findViewById(R.id.btn_right_answerboard).setOnClickListener(this);
        btn_right_textsize.setOnClickListener(this);
        btn_last.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterExam(getSupportFragmentManager());
        pager.setAdapter(adapterPager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                text_exam_num.setText(position + 1 + "");
                text_exam_type.setText(questions.get(position).getTypeName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        text_exam_num.setText("1");
    }

    private void initData() {
        netQueryQuestions();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                onBackPressed();
                break;
            case R.id.btn_last:
                ViewPagerUtil.last(pager);
                break;
            case R.id.btn_next:
                ViewPagerUtil.next(pager);
                break;
            case R.id.btn_right_answerboard:
                AnswerBoardActivity.start(this, questions);
                break;
            case R.id.btn_right_textsize:
                popTextSize.showPopupWindow(btn_right_textsize);
                break;
            case R.id.btn_right_favo:
                break;
        }
    }

    private void netQueryQuestions() {
        Map<String, Object> param = new NetParam()
                .put("paperId", paperId)
                .build();
        showLoadingDialog();
        NetApi.NI().queryQuestions(param).enqueue(new BaseCallback<List<Examination>>(new TypeToken<List<Examination>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Examination> examinations, String msg) {
                questions = (ArrayList<QuestionBean>) AppHelper.Exam.transExamination2QuestionBeans(examinations);
                adapterPager.getResults().clear();
                adapterPager.getResults().addAll(questions);
                adapterPager.notifyDataSetChanged();
                text_exam_count.setText("/" + adapterPager.getCount());
                hideLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
