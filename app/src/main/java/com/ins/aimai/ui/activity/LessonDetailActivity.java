package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.Study;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.StatusHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetHelper;
import com.ins.aimai.ui.adapter.PagerAdapterLessonDetail;
import com.ins.aimai.ui.adapter.RecycleAdapterLable;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

public class LessonDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterLessonDetail adapterPager;
    private RecyclerView recycler;
    private RecycleAdapterLable adapter;

    private ImageView img_lessondetail_cover;
    private TextView text_lessondetail_title;
    private TextView text_lessondetail_count;
    private TextView text_lessondetail_time;
    private TextView text_lessondetail_price;
    private TextView text_lessondetail_salecount;
    private TextView btn_lessondetail_watchcount;
    private TextView btn_lessondetail_unwatchcount;
    private TextView btn_lessondetail_testcount;
    private TextView btn_lessondetail_countalloc;

    private View lay_lessondetail_btn_comp;
    private View lay_lessondetail_btn_user;

    private String[] titles = new String[]{"介绍", "目录", "评论"};

    private int type;   //0:课程，1：订单
    private int lessonId;
    private int orderId;
    private int countAlloc;
    private int countAll;
    private Lesson lesson;

    public static void start(Context context, int lessonId) {
        Intent intent = new Intent(context, LessonDetailActivity.class);
        intent.putExtra("lessonId", lessonId);
        context.startActivity(intent);
    }

    public static void startByOrder(Context context, Study study) {
        startByOrder(context, study.getOrderId(), study.getAllocationNum(), study.getNumber());
    }

    public static void startByOrder(Context context, int orderId, int countAlloc, int countAll) {
        Intent intent = new Intent(context, LessonDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("countAlloc", countAlloc);
        intent.putExtra("countAll", countAll);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_LESSON_ALLOCAT) {
            int count = (int) event.get("count");
            countAlloc += count;
            setData(lesson);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessondetail);
        registEventBus();
        setToolbar();
        toolbar.bringToFront();
        StatusBarTextUtil.transparencyBar(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("lessonId")) {
            lessonId = getIntent().getIntExtra("lessonId", 0);
            type = 0;
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
            type = 1;
        }
        if (getIntent().hasExtra("countAlloc")) {
            countAlloc = getIntent().getIntExtra("countAlloc", 0);
        }
        if (getIntent().hasExtra("countAll")) {
            countAll = getIntent().getIntExtra("countAll", 0);
        }
    }

    private void initView() {
        img_lessondetail_cover = (ImageView) findViewById(R.id.img_lessondetail_cover);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);

        text_lessondetail_title = (TextView) findViewById(R.id.text_lessondetail_title);
        text_lessondetail_count = (TextView) findViewById(R.id.text_lessondetail_count);
        text_lessondetail_time = (TextView) findViewById(R.id.text_lessondetail_time);
        text_lessondetail_price = (TextView) findViewById(R.id.text_lessondetail_price);
        text_lessondetail_salecount = (TextView) findViewById(R.id.text_lessondetail_salecount);
        btn_lessondetail_watchcount = (TextView) findViewById(R.id.btn_lessondetail_watchcount);
        btn_lessondetail_unwatchcount = (TextView) findViewById(R.id.btn_lessondetail_unwatchcount);
        btn_lessondetail_testcount = (TextView) findViewById(R.id.btn_lessondetail_testcount);
        btn_lessondetail_countalloc = (TextView) findViewById(R.id.btn_lessondetail_countalloc);
        lay_lessondetail_btn_comp = findViewById(R.id.lay_lessondetail_btn_comp);
        lay_lessondetail_btn_user = findViewById(R.id.lay_lessondetail_btn_user);
        findViewById(R.id.btn_go).setOnClickListener(this);
        findViewById(R.id.btn_go_allot).setOnClickListener(this);
        btn_lessondetail_watchcount.setOnClickListener(this);
        btn_lessondetail_unwatchcount.setOnClickListener(this);
        btn_lessondetail_testcount.setOnClickListener(this);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterLessonDetail(getSupportFragmentManager(), titles, lessonId);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);

        //设置lable
        adapter = new RecycleAdapterLable(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);

        //根据不同身份设置不同状态
        int status = StatusHelper.lessonDetailBtn(type);
        switch (status) {
            case 0:
                lay_lessondetail_btn_user.setVisibility(View.VISIBLE);
                lay_lessondetail_btn_comp.setVisibility(View.GONE);
                break;
            case 1:
                lay_lessondetail_btn_user.setVisibility(View.GONE);
                lay_lessondetail_btn_comp.setVisibility(View.VISIBLE);
                break;
            case 2:
                lay_lessondetail_btn_user.setVisibility(View.GONE);
                lay_lessondetail_btn_comp.setVisibility(View.GONE);
                break;
        }
        if (type == 0) {
            text_lessondetail_salecount.setVisibility(View.GONE);
        } else {
            text_lessondetail_salecount.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        netQueryLessonDetail();
    }

    private void setData(Lesson lesson) {
        if (lesson != null) {
            GlideUtil.loadImg(img_lessondetail_cover, R.drawable.default_bk_img, lesson.getCover());
            text_lessondetail_title.setText(lesson.getCurriculumName());
            text_lessondetail_count.setText(lesson.getVideoNum() + "个视频课");
            text_lessondetail_time.setText(TimeUtil.formatSecond(lesson.getHdSeconds()));
            text_lessondetail_price.setText("￥" + AppHelper.formatPrice(lesson.getPrice()));
            text_lessondetail_salecount.setText(countAll + "份");
            btn_lessondetail_watchcount.setText(0 + "人已观看");
            btn_lessondetail_unwatchcount.setText(0 + "人未观看");
            btn_lessondetail_testcount.setText(0 + "人已考核");
            btn_lessondetail_countalloc.setText(countAlloc + "/" + countAll);

            adapter.getResults().clear();
            adapter.getResults().add(new TestBean());
            adapter.getResults().add(new TestBean());
            adapter.getResults().add(new TestBean());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lessondetail_watchcount:
                LearnUserActivity.start(this);
                break;
            case R.id.btn_lessondetail_unwatchcount:
                LearnUserActivity.start(this);
                break;
            case R.id.btn_lessondetail_testcount:
                LearnUserActivity.start(this);
                break;
            case R.id.btn_go:
                PayDialogActivity.start(this, lessonId);
                break;
            case R.id.btn_go_allot:
                if (countAlloc<countAll) {
                    SortUserActivity.start(this, orderId);
                }else {
                    ToastUtil.showToastShort("所购课程已全部分配完");
                }
                break;
        }
    }

    private void postIntro(String intro) {
        EventBean eventBean = new EventBean(EventBean.EVENT_LESSONDETAIL_INTRO);
        eventBean.put("intro", intro);
        EventBus.getDefault().post(eventBean);
    }

    private void postDirectory(List<CourseWare> courseWares) {
        if (!StrUtil.isEmpty(courseWares)) {
            EventBean eventBean = new EventBean(EventBean.EVENT_LESSONDETAIL_DIRECTORY);
            eventBean.put("courseWares", courseWares);
            EventBus.getDefault().post(eventBean);
        }
    }

    private void netQueryLessonDetail() {
        NetHelper.getInstance().netQueryLessonDetail(type, lessonId, orderId, new NetHelper.OnLessonCallback() {
            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onSuccess(int status, Lesson lesson, String msg) {
                LessonDetailActivity.this.lesson = lesson;
                setData(lesson);
                postIntro(lesson.getCurriculumDescribe());
                postDirectory(lesson.getCourseWares());
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
