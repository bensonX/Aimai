package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.ExamModelOffi;
import com.ins.aimai.bean.ExamPractice;
import com.ins.aimai.bean.ExamResultPojo;
import com.ins.aimai.bean.Examination;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.ExamCountDownTimer;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetExamHelper;
import com.ins.aimai.net.helper.NetFavoHelper;
import com.ins.aimai.ui.adapter.PagerAdapterExam;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.DialogSureAimai;
import com.ins.aimai.ui.dialog.PopTextSize;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ClearCacheUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.TimeUtil;
import com.ins.common.utils.ViewPagerUtil;

import org.greenrobot.eventbus.EventBus;

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
    private TextView text_time;
    private View btn_pause;

    private PopTextSize popTextSize;
    private ExamCountDownTimer timer;

    private ArrayList<QuestionBean> questions;
    private int type;
    private int paperId;
    private int orderId;
    private int useTime;    //考试时长（模拟题和正式考试题）

    public static void startPractice(Context context, ExamPractice examPractice) {
        start(context, 0, examPractice.getPaperId(), examPractice.getOrderId(), 0);
    }

    public static void startMoldel(Context context, ExamModelOffi exam) {
        start(context, 1, exam.getPaperId(), exam.getOrderId(), exam.getUseTime());
    }

    public static void startOfficial(Context context, ExamModelOffi exam) {
        start(context, 2, exam.getPaperId(), exam.getOrderId(), exam.getUseTime());
    }

    private static void start(Context context, int type, int paperId, int orderId, int useTime) {
        Intent intent = new Intent(context, ExamActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("paperId", paperId);
        intent.putExtra("orderId", orderId);
        intent.putExtra("useTime", useTime);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_EXAMBOARD_SELECT) {
            int position = (int) event.get("position");
            ViewPagerUtil.goPosition(pager, position, false);
        } else if (event.getEvent() == EventBean.EVENT_EXAM_SUBMITED) {
            finish();
        } else if (event.getEvent() == EventBean.EVENT_EXAM_TIME) {
            int time = (int) event.get("time");
            setTimeNote(time);
            //最后5秒弹窗窗口提示（练习题例外）
            if (time <= 5 && type != 0)
                ExamDialogActivity.start(this, questions, paperId, orderId, type);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
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
        if (getIntent().hasExtra("useTime")) {
            useTime = getIntent().getIntExtra("useTime", 0);
        }
        timer = new ExamCountDownTimer(1 * 15);
        popTextSize = new PopTextSize(this);
        popTextSize.setNeedanim(false);
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        text_time = (TextView) findViewById(R.id.text_toolbar_title);
        btn_pause = findViewById(R.id.btn_pause);
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
        btn_pause.setOnClickListener(this);
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
        //根据不同实体类型设置不同的可见性
        switch (type) {
            case 0:
                text_time.setVisibility(View.GONE);
                btn_pause.setVisibility(View.GONE);
                break;
            case 1:
                text_time.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.VISIBLE);
                setTimeNote(useTime);
                break;
            case 2:
                text_time.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
                setTimeNote(useTime);
                break;
        }
    }

    private void initData() {
        netQueryQuestions();
    }

    private void setTimeNote(int time) {
        //时间小于10分钟要显示橙色
        text_time.setTextColor(ContextCompat.getColor(this, time <= 10 * 60 ? R.color.am_orage : R.color.com_text_blank));
        text_time.setText(TimeUtil.formatSecond(time * 1000, "HH:mm:ss"));
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
                AnswerBoardActivity.start(this, questions, paperId, orderId, type);
                break;
            case R.id.btn_right_textsize:
                popTextSize.showPopupWindow(btn_right_textsize);
                break;
            case R.id.btn_right_favo:
                NetFavoHelper.getInstance().netAddCollect(questions.get(pager.getCurrentItem()).getId(), 2);
                break;
            case R.id.btn_pause:
                if (btn_pause.isSelected()) {
                    timePause();
                } else {
                    timeStart();
                }
                break;
        }
    }

    private void timeStart() {
        timer.start();
        btn_pause.setSelected(true);
    }

    private void timePause() {
        timer.cancel();
        btn_pause.setSelected(false);
    }

    @Override
    public void onBackPressed() {
        DialogSure.showDialog(this, "考试中途退出将会取消该次考试，确定退出？", new DialogSure.CallBack() {
            @Override
            public void onSure() {
                ExamActivity.super.onBackPressed();
            }
        });
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
                //不是练习题则开始计时
                if (type != 0) timeStart();
                hideLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }

    //####################  get & set ########################
    public List<QuestionBean> getQuestions() {
        return questions;
    }

    public int getPaperId() {
        return paperId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getType() {
        return type;
    }
}
