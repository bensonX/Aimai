package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ins.aimai.R;
import com.ins.aimai.bean.SortBean;
import com.ins.aimai.ui.adapter.RecycleAdapterSortUser;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ColorUtil;
import com.ins.aimai.utils.SortUtil;
import com.ins.common.common.ItemDecorationSortStickTop;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.utils.FocusUtil;
import com.ins.common.view.IndexBar;
import com.ins.common.view.SideBar;

import java.util.ArrayList;
import java.util.List;

public class SortUserActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private View showin;
    private ViewGroup showingroup;

    private EditText edit_query;
    private IndexBar index_bar;
    private RecyclerView recycler;
    private RecycleAdapterSortUser adapter;
    private ItemDecorationSortStickTop decoration;
    private List<SortBean> results = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, SortUserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_user);
        setToolbar();
        initView();
        initCtrl();
        initData();
    }

    public void initView() {
        showingroup = (ViewGroup) findViewById(R.id.showingroup);
        edit_query = (EditText) findViewById(R.id.edit_query);
        index_bar = (IndexBar) findViewById(R.id.index_bar);
        recycler = (RecyclerView) findViewById(R.id.rl_recycle_view);
        findViewById(R.id.btn_right).setOnClickListener(this);
        FocusUtil.focusToTop(recycler);
    }

    public void initCtrl() {
        adapter = new RecycleAdapterSortUser(this);
        recycler.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        recycler.addItemDecoration(decoration = new ItemDecorationSortStickTop(this, ColorUtil.colors));
        recycler.setAdapter(adapter);

        index_bar.setColors(ColorUtil.colors);
        index_bar.addOnIndexChangeListener(new SideBar.OnIndexChangeListener() {
            @Override
            public void onIndexChanged(float centerY, String tag, int position) {
                int pos = SortUtil.getPosByTag(results, tag);
                if (pos != -1) layoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });

        edit_query.setHint(makeSearchHint());
        edit_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData() {
        showin = LoadingViewHelper.showin(showingroup, R.layout.layout_loading, showin);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String[] names = {"孙尚香", "安其拉", "白起", "不知火舞", "@小马快跑", "_德玛西亚之力_", "妲己", "狄仁杰", "典韦", "韩信",
                        "老夫子", "刘邦", "刘禅", "鲁班七号", "墨子", "孙膑", "孙尚香", "孙悟空", "项羽", "亚瑟",
                        "周瑜", "庄周", "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
                        "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
                        "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙",};
                for (String name : names) {
                    SortBean bean = new SortBean();
                    bean.setName(name);
                    results.add(bean);
                }
                freshData(results);
                LoadingViewHelper.showout(showingroup, showin);
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                break;
        }
    }

    private void freshData(List<SortBean> results) {
        SortUtil.sortData(results);
        String tagsStr = SortUtil.getTags(results);
        List<String> tagsArr = SortUtil.getTagsArr(results);
        index_bar.setIndexStr(tagsStr);
        decoration.setTags(tagsArr);
        adapter.getResults().clear();
        adapter.getResults().addAll(results);
        adapter.notifyDataSetChanged();
    }

    public void search(String filterStr) {
        List<SortBean> resultsSort = new ArrayList<>();
        for (SortBean sortBean : results) {
            if (SortUtil.match(sortBean, filterStr)) {
                resultsSort.add(sortBean);
            }
        }
        freshData(resultsSort);
    }

    //给Edit设置Hint文字（带图片）
    private SpannableString makeSearchHint(){
        SpannableString spannableString = new SpannableString("1 搜索");
        Drawable drawable = getResources().getDrawable(R.drawable.ic_home_search_edit);
        drawable.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
