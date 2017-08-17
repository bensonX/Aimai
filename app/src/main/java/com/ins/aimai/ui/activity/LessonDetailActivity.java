package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.Order;
import com.ins.aimai.bean.Study;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.StatusHelper;
import com.ins.aimai.net.helper.NetFavoHelper;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private View lay_lessondetail_count;
    private TextView btn_lessondetail_unwatchcount;
    private TextView btn_lessondetail_testcount;
    private TextView btn_lessondetail_countalloc;

    private View lay_lessondetail_btn_comp;
    private View lay_lessondetail_btn_user;
    private View btn_right;

    private String[] titles = new String[]{"介绍", "目录", "评论"};

    private int type;   //0:课程，1：订单
    private int lessonId;
    private int orderId;
    private Lesson lesson;

    //课程详情页面
    public static void startByLesson(Context context, int lessonId) {
        Intent intent = new Intent(context, LessonDetailActivity.class);
        intent.putExtra("lessonId", lessonId);
        intent.putExtra("type", 0);
        context.startActivity(intent);
    }

    public static void startByOrder(Context context, Study study) {
        startByOrder(context, study.getOrderId(), study.getId());
    }

    public static void startByOrder(Context context, Order order) {
        startByOrder(context, order.getId(), order.getCurriculumId());
    }

    //订单页面
    private static void startByOrder(Context context, int orderId, int lessonId) {
        if (AppData.App.getUser() != null) {
            Intent intent = new Intent(context, LessonDetailActivity.class);
            intent.putExtra("orderId", orderId);
            intent.putExtra("type", 1);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_USER_ALLOCAT) {
            int count = (int) event.get("count");
            lesson.setAllocationNum(lesson.getAllocationNum() + count);
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
        StatusBarTextUtil.StatusBarLightMode(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (getIntent().hasExtra("lessonId")) {
            lessonId = getIntent().getIntExtra("lessonId", 0);
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
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
        lay_lessondetail_count = findViewById(R.id.lay_lessondetail_count);
        btn_lessondetail_unwatchcount = (TextView) findViewById(R.id.btn_lessondetail_unwatchcount);
        btn_lessondetail_testcount = (TextView) findViewById(R.id.btn_lessondetail_testcount);
        btn_lessondetail_countalloc = (TextView) findViewById(R.id.btn_lessondetail_countalloc);
        lay_lessondetail_btn_comp = findViewById(R.id.lay_lessondetail_btn_comp);
        lay_lessondetail_btn_user = findViewById(R.id.lay_lessondetail_btn_user);
        btn_right = findViewById(R.id.btn_right);
        findViewById(R.id.btn_go).setOnClickListener(this);
        findViewById(R.id.btn_go_allot).setOnClickListener(this);
        btn_lessondetail_watchcount.setOnClickListener(this);
        btn_lessondetail_testcount.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterLessonDetail(getSupportFragmentManager(), titles, lessonId);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
        setTab();

        //设置lable
        adapter = new RecycleAdapterLable(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);

        //根据不同身份设置按钮不同状态
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
        //设置收藏按钮和购买数量可见性
        if (type == 0) {
            //课程
            text_lessondetail_salecount.setVisibility(View.GONE);
            btn_right.setVisibility(View.VISIBLE);
        } else {
            //订单
            text_lessondetail_salecount.setVisibility(View.VISIBLE);
            btn_right.setVisibility(View.GONE);
        }
        //设置观看人员可见性
        if (!AppHelper.isUser() && type == 1) {
            lay_lessondetail_count.setVisibility(View.VISIBLE);
        } else {
            lay_lessondetail_count.setVisibility(View.GONE);
        }
    }

    private void initData() {
        netQueryLessonDetail();
    }

    private void setData(Lesson lesson) {
        if (lesson != null) {
            setToolbar(lesson.getCurriculumName());
            GlideUtil.loadImg(img_lessondetail_cover, R.drawable.default_bk_img, lesson.getCover());
            text_lessondetail_title.setText(lesson.getCurriculumName());
            text_lessondetail_count.setText(lesson.getVideoNum() + "个视频课");
            text_lessondetail_time.setText(TimeUtil.formatSecond(lesson.getHdSeconds()));
            text_lessondetail_price.setText("￥" + AppHelper.formatPrice(lesson.getPrice()));
            text_lessondetail_salecount.setText(lesson.getNumber() + "份");
            btn_lessondetail_watchcount.setText(lesson.getWatchNum() + "人已观看");
            btn_lessondetail_unwatchcount.setText(lesson.getCountUser() - lesson.getWatchNum() + "人未观看");
            btn_lessondetail_testcount.setText(lesson.getFinishExamine() + "人已考核");
            btn_lessondetail_countalloc.setText(lesson.getAllocationNum() + "/" + lesson.getNumber());

            //设置标签数据
            adapter.getResults().clear();
            if (!TextUtils.isEmpty(lesson.getStageName()))
                adapter.getResults().add(lesson.getStageName());
            if (!TextUtils.isEmpty(lesson.getTypeName()))
                adapter.getResults().add(lesson.getTypeName());
            if (lesson.getYear() != 0)
                adapter.getResults().add(TimeUtil.getTimeFor("yyyy年", new Date(lesson.getYear())));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lessondetail_watchcount:
                LearnUserActivity.startWatch(this, lesson.getWatchNum());
                break;
            case R.id.btn_lessondetail_testcount:
                LearnUserActivity.startExamed(this, lesson.getFinishExamine());
                break;
            case R.id.btn_go:
                PayDialogActivity.start(this, lessonId);
                break;
            case R.id.btn_right:
                NetFavoHelper.getInstance().netAddCollect(lessonId, 1);
                break;
            case R.id.btn_go_allot:
                if (lesson.getAllocationNum() < lesson.getNumber()) {
                    SortUserActivity.start(this, orderId);
                } else {
                    ToastUtil.showToastShort("所购课程已全部分配完");
                }
                break;
        }
    }

    //设置TabLayout为自定义样式
    private void setTab() {
        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab t = tab.getTabAt(i);
            if (t.getCustomView() == null) {
                t.setCustomView(LayoutInflater.from(this).inflate(R.layout.layout_tab, tab, false));
            }
            TextView textView = (TextView) ((ViewGroup) t.getCustomView()).getChildAt(0);
            textView.setTextSize(14);
            if (i == 0) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_lessontab_intro_select, 0, 0, 0);
            } else if (i == 1) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_lessontab_directory_select, 0, 0, 0);
            } else if (i == 2) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_lessontab_comment_select, 0, 0, 0);
            }
            textView.setText(t.getText());
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
