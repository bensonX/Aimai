package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.GridAdapterLessonComp;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;

import java.util.List;
import java.util.Map;

public class CompDetailActivity extends BaseAppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private GridView grid;
    private GridAdapterLessonComp adapter;

    private ImageView img_compdetail_header;
    private TextView text_compdetail_name;
    private TextView text_compdetail_position;
    private TextView text_compdetail_count_learn;
    private TextView text_compdetail_count_safe;
    private TextView text_compdetail_count_unpass;
    private View lay_compdetail_lesson;

    private User comp;

    public static void start(Context context, User comp) {
        Intent intent = new Intent(context, CompDetailActivity.class);
        intent.putExtra("comp", comp);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compdetail);
        StatusBarTextUtil.transparencyBar(this);
        StatusBarTextUtil.StatusBarLightMode(this);
        setToolbar();
        toolbar.bringToFront();
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(toolbar);
    }

    private void initBase() {
        if (getIntent().hasExtra("comp")) {
            comp = (User) getIntent().getSerializableExtra("comp");
        }
    }

    private void initView() {
        img_compdetail_header = (ImageView) findViewById(R.id.img_compdetail_header);
        text_compdetail_name = (TextView) findViewById(R.id.text_compdetail_name);
        text_compdetail_position = (TextView) findViewById(R.id.text_compdetail_position);
        text_compdetail_count_learn = (TextView) findViewById(R.id.text_compdetail_count_learn);
        text_compdetail_count_safe = (TextView) findViewById(R.id.text_compdetail_count_safe);
        text_compdetail_count_unpass = (TextView) findViewById(R.id.text_compdetail_count_unpass);
        lay_compdetail_lesson = findViewById(R.id.lay_compdetail_lesson);
        grid = (GridView) findViewById(R.id.grid);
        findViewById(R.id.lay_compdetail_header).setOnClickListener(this);
        //设置可见性
        lay_compdetail_lesson.setVisibility(View.GONE);
    }

    private void initCtrl() {
        adapter = new GridAdapterLessonComp(this);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(this);
        setBaseData(comp);
    }

    private void initData() {
        netQueryCompDetail();
        netQueryLessonsByCompId();
    }

    private void setBaseData(User comp) {
        if (comp != null) {
            GlideUtil.loadCircleImg(img_compdetail_header, R.drawable.default_header, comp.getAvatar());
            text_compdetail_name.setText(comp.getShowName());
            text_compdetail_position.setText(AppHelper.Gov.getCompAddressStr(comp));
        }
    }

    private void setUserData(User comp) {
        if (comp != null) {
            text_compdetail_count_learn.setText(comp.getJoinNum() + "位\n参培人员");
            text_compdetail_count_safe.setText(comp.getSafeNum() + "位\n安全人员");
            text_compdetail_count_unpass.setText(comp.getJoinNum() - comp.getSafeNum() + "位\n未通过人员");
        }
    }


    private void setLessonData(List<Lesson> lessons) {
        if (StrUtil.isEmpty(lessons)) {
            lay_compdetail_lesson.setVisibility(View.GONE);
        } else {
            lay_compdetail_lesson.setVisibility(View.VISIBLE);
            adapter.getResults().clear();
            adapter.getResults().addAll(lessons);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_compdetail_header:
                CompInfoActivity.start(this, comp);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Lesson lesson = adapter.getResults().get(position);
        LessonEmployActivity.start(this, lesson);
    }

    private void netQueryCompDetail() {
        Map<String, Object> param = new NetParam()
                .put("companyId", comp.getId())
                .build();
        NetApi.NI().queryCompDetail(param).enqueue(new BaseCallback<User>(User.class) {
            @Override
            public void onSuccess(int status, User comp, String msg) {
                CompDetailActivity.this.comp = comp;
                setUserData(comp);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    private void netQueryLessonsByCompId() {
        Map<String, Object> param = new NetParam()
                .put("companyId", comp.getId())
                .put("pageNO", 1)
                .put("pageSize", 1000)
                .build();
        showLoadingDialog();
        NetApi.NI().queryLessonsByCompId(param).enqueue(new BaseCallback<List<Lesson>>(new TypeToken<List<Lesson>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Lesson> lessons, String msg) {
                setLessonData(lessons);
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
