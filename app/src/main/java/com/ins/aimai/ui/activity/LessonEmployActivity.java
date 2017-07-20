package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.ui.adapter.PagerAdapterFavo;
import com.ins.aimai.ui.adapter.PagerAdapterLessonEmploy;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TabLayoutUtil;

public class LessonEmployActivity extends BaseAppCompatActivity {

    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterLessonEmploy adapterPager;
    private ImageView img_lessonemploy_pic;

    private String[] titles = new String[]{"观看人员", "安全人员"};

    public static void start(Context context) {
        Intent intent = new Intent(context, LessonEmployActivity.class);
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
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        img_lessonemploy_pic = (ImageView) findViewById(R.id.img_lessonemploy_pic);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterLessonEmploy(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);
        GlideUtil.loadImgTest(img_lessonemploy_pic);

        setTab();
    }

    private void initData() {
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
}
