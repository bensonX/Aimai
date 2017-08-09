package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.common.StatusHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.net.helper.NetFavoHelper;
import com.ins.aimai.net.helper.NetHelper;
import com.ins.aimai.ui.adapter.ListAdapterDirectory;
import com.ins.aimai.ui.adapter.PagerAdapterLessonDetail;
import com.ins.aimai.ui.adapter.RecycleAdapterLable;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.TimeUtil;
import com.ins.common.view.ListViewLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class LessonDetailCompActivity extends BaseAppCompatActivity {

    private RecyclerView recycler;
    private RecycleAdapterLable adapter;
    private ListViewLinearLayout list_directory;
    private ListAdapterDirectory adapterDirectory;

    private ImageView img_lessondetail_cover;
    private TextView text_lessondetail_title;
    private TextView text_lessondetail_count;
    private TextView text_lessondetail_time;
    private TextView text_lessondetail_status;
    private TextView text_lessondetail_passtime;
    private View lay_lessondetailcomp_list;

    private View btn_right;

    private int orderId;
    private Lesson lesson;

    public static void start(Context context, Lesson lesson) {
        Intent intent = new Intent(context, LessonDetailCompActivity.class);
        intent.putExtra("orderId", lesson.getOrderId());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessondetailcomp);
        setToolbar();
        toolbar.bringToFront();
        StatusBarTextUtil.transparencyBar(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
            //测试订单ID：88
            //orderId = 88;
        }
    }

    private void initView() {
        img_lessondetail_cover = (ImageView) findViewById(R.id.img_lessondetail_cover);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        list_directory = (ListViewLinearLayout) findViewById(R.id.list_directory);

        text_lessondetail_title = (TextView) findViewById(R.id.text_lessondetail_title);
        text_lessondetail_count = (TextView) findViewById(R.id.text_lessondetail_count);
        text_lessondetail_time = (TextView) findViewById(R.id.text_lessondetail_time);
        text_lessondetail_status = (TextView) findViewById(R.id.text_lessondetail_status);
        text_lessondetail_passtime = (TextView) findViewById(R.id.text_lessondetail_passtime);
        lay_lessondetailcomp_list = findViewById(R.id.lay_lessondetailcomp_list);

        lay_lessondetailcomp_list.setVisibility(View.GONE);
    }

    private void initCtrl() {
        //设置lable
        adapter = new RecycleAdapterLable(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);
        //设置目录
        adapterDirectory = new ListAdapterDirectory(this);
        list_directory.setAdapter(adapterDirectory);

        FontUtils.boldText(text_lessondetail_status);
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
            //TODO:status 和 passtime 目前还没有数据
            text_lessondetail_status.setText(lesson.isPass() ? "考核通过" : "待考核");
            text_lessondetail_passtime.setText(TimeUtil.getTimeFor("yyyy-MM-dd\nHH:mm", new Date(lesson.getCreateTime())));

            //设置标签数据
            adapter.getResults().clear();
            if (!TextUtils.isEmpty(lesson.getStageName()))
                adapter.getResults().add(lesson.getStageName());
            if (!TextUtils.isEmpty(lesson.getTypeName()))
                adapter.getResults().add(lesson.getTypeName());
            if (lesson.getYear() != 0)
                adapter.getResults().add(TimeUtil.getTimeFor("yyyy年", new Date(lesson.getYear())));
            adapter.notifyDataSetChanged();

            List<Video> videos = AppHelper.VideoPlay.convertVideosByCourseWares(lesson.getCourseWares());
            if (StrUtil.isEmpty(videos)) {
                lay_lessondetailcomp_list.setVisibility(View.GONE);
            } else {
                lay_lessondetailcomp_list.setVisibility(View.VISIBLE);
                adapterDirectory.getResults().addAll(videos);
                adapterDirectory.notifyDataSetChanged();
            }
        }
    }

    private void netQueryLessonDetail() {
        Map<String, Object> param = new NetParam()
                .put("orderId", orderId)
                .build();
        showLoadingDialog();
        NetApi.NI().queryLessonDetailByOrder(param).enqueue(new BaseCallback<Lesson>(Lesson.class) {
            @Override
            public void onSuccess(int status, Lesson lesson, String msg) {
                LessonDetailCompActivity.this.lesson = lesson;
                setData(lesson);
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
