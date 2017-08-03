package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.adapter.GridAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;

import java.util.List;

public class UserDetailActivity extends BaseAppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private GridView grid;
    private GridAdapterLesson adapter;

    private ImageView img_userdetail_header;
    private TextView text_userdetail_comp;
    private TextView text_userdetail_name;
    private TextView text_userdetail_name2;
    private TextView text_userdetail_phone;
    private TextView text_userdetail_idcard;
    private TextView text_userdetail_department;
    private TextView text_userdetail_job;
    private View lay_userdetail_lesson;
    private View btn_go;
    private View btn_right;

    private User user;
    private int type;

    public static void start(Context context, User user) {
        start(context, user, 0);
    }

    public static void start(Context context, User user, int type) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_LESSON_ALLOCAT) {
            initData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);
        StatusBarTextUtil.transparencyBar(this);
        StatusBarTextUtil.StatusBarLightMode(this);
        setToolbar();
        toolbar.bringToFront();
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(toolbar);
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
        }
    }

    private void initView() {
        grid = (GridView) findViewById(R.id.grid);
        img_userdetail_header = (ImageView) findViewById(R.id.img_userdetail_header);
        text_userdetail_comp = (TextView) findViewById(R.id.text_userdetail_comp);
        text_userdetail_name = (TextView) findViewById(R.id.text_userdetail_name);
        text_userdetail_name2 = (TextView) findViewById(R.id.text_userdetail_name2);
        text_userdetail_phone = (TextView) findViewById(R.id.text_userdetail_phone);
        text_userdetail_idcard = (TextView) findViewById(R.id.text_userdetail_idcard);
        text_userdetail_department = (TextView) findViewById(R.id.text_userdetail_department);
        text_userdetail_job = (TextView) findViewById(R.id.text_userdetail_job);
        lay_userdetail_lesson = findViewById(R.id.lay_userdetail_lesson);
        btn_right = findViewById(R.id.btn_right);
        btn_go = findViewById(R.id.btn_go);
        btn_right.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        //根据启动类型设置可见性
        if (type == 0) {
            btn_right.setVisibility(View.GONE);
            btn_go.setVisibility(View.VISIBLE);
        } else {
            btn_right.setVisibility(View.VISIBLE);
            btn_go.setVisibility(View.GONE);
        }
    }

    private void initCtrl() {
        adapter = new GridAdapterLesson(this);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
        setData(user);
    }

    private void initData() {
        adapter.getResults().clear();
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.notifyDataSetChanged();
    }

    private void setData(User user) {
        if (user != null) {
            GlideUtil.loadCircleImg(img_userdetail_header, R.drawable.default_header, user.getAvatar());
            text_userdetail_name.setText(user.getShowName());
            text_userdetail_name2.setText(user.getShowName());
            text_userdetail_comp.setText(user.getCompanyName());
            text_userdetail_phone.setText(user.getPhone());
            text_userdetail_idcard.setText(user.getPid());
            text_userdetail_department.setText(user.getDepartmentName());
            text_userdetail_job.setText(user.getJobTitle());
        }
    }

    private void setLessonData(List<Lesson> lessons) {
        if (StrUtil.isEmpty(lessons)) {
            lay_userdetail_lesson.setVisibility(View.GONE);
        } else {
            lay_userdetail_lesson.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                LessonAllocatActivity.start(this, user.getId());
                break;
            case R.id.btn_right:
                finish();
                EmployAddActivity.start(this, user);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
