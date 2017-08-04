package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.ui.adapter.PagerAdapterLessonEmploy;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.TabLayoutUtil;
import com.ins.common.utils.TimeUtil;

public class LessonEmployActivity extends BaseAppCompatActivity {

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterLessonEmploy adapterPager;
    private ImageView img_lessonemploy_pic;
    private TextView text_lessonemploy_title;
    private TextView text_lessonemploy_count_video;
    private TextView text_lessonemploy_time;

    private String[] titles = new String[]{"观看人员", "安全人员"};

    private Lesson lesson;

    public static void start(Context context, Lesson lesson) {
        Intent intent = new Intent(context, LessonEmployActivity.class);
        intent.putExtra("lesson", lesson);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessonemploy);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("lesson")) {
            lesson = (Lesson) getIntent().getSerializableExtra("lesson");
        }
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        img_lessonemploy_pic = (ImageView) findViewById(R.id.img_lessonemploy_pic);
        text_lessonemploy_title = (TextView) findViewById(R.id.text_lessonemploy_title);
        text_lessonemploy_count_video = (TextView) findViewById(R.id.text_lessonemploy_count_video);
        text_lessonemploy_time = (TextView) findViewById(R.id.text_lessonemploy_time);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterLessonEmploy(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
        setTab();
    }

    private void initData() {
        setData(lesson);
    }

    private void setData(Lesson lesson) {
        if (lesson != null) {
            GlideUtil.loadImg(img_lessonemploy_pic, R.drawable.default_bk_img, lesson.getCover());
            text_lessonemploy_title.setText(lesson.getCurriculumName());
            text_lessonemploy_count_video.setText(lesson.getVideoNum() + "个视频课程");
            text_lessonemploy_time.setText(TimeUtil.formatSecond(lesson.getHdSeconds()));
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
            if (i == 0) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video_start, 0, 0, 0);
            } else if (i == 1) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_learn_user, 0, 0, 0);
            }
            textView.setText(t.getText());
        }
    }

    //设置Tab的标题，同时更新UI样式
    public void setTabTextCount(int position, int count) {
        TabLayoutUtil.setTab(tab, position, titles[position] + "(" + count + ")");
        setTab();
    }

    //################ get & set ###################

    public Lesson getLesson() {
        return lesson;
    }
}
