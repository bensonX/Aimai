package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CommonBean;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.common.AppVali;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.RecycleAdapterSortUser;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.utils.ColorUtil;
import com.ins.aimai.utils.SortUtil;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.common.ItemDecorationSortStickTop;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.IndexBar;
import com.ins.common.view.LoadingLayout;
import com.ins.common.view.SideBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SortUserActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private LoadingLayout loadingLayout;

    private EditText edit_query;
    private IndexBar index_bar;
    private RecyclerView recycler;
    private RecycleAdapterSortUser adapter;
    private ItemDecorationSortStickTop decoration;
    private LinearLayoutManager layoutManager;
    private View text_sort_selectall;

    private int lessonId;
    private List<User> users;

    public static void start(Context context, int lessonId) {
        Intent intent = new Intent(context, SortUserActivity.class);
        intent.putExtra("lessonId", lessonId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_user);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("lessonId")) {
            lessonId = getIntent().getIntExtra("lessonId", 0);
        }
    }

    public void initView() {
        loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        edit_query = (EditText) findViewById(R.id.edit_query);
        index_bar = (IndexBar) findViewById(R.id.index_bar);
        recycler = (RecyclerView) findViewById(R.id.rl_recycle_view);
        text_sort_selectall = findViewById(R.id.text_sort_selectall);
        text_sort_selectall.setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
        FocusUtil.focusToTop(recycler);
    }

    public void initCtrl() {
        adapter = new RecycleAdapterSortUser(this);
        adapter.setSelectAllView(text_sort_selectall);
        recycler.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        recycler.addItemDecoration(decoration = new ItemDecorationSortStickTop(this, ColorUtil.colors));
        recycler.setAdapter(adapter);
        loadingLayout.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        index_bar.setColors(ColorUtil.colors);
        index_bar.addOnIndexChangeListener(new SideBar.OnIndexChangeListener() {
            @Override
            public void onIndexChanged(float centerY, String tag, int position) {
                int pos = SortUtil.getPosByTag(adapter.getResults(), tag);
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
        netQueryUserAlloc();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                final String ids = adapter.getSelectedIds();
                String msg = AppVali.allocatUser(ids);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    DialogSure.showDialog(this, "确定要为这些员工分配课程？", new DialogSure.CallBack() {
                        @Override
                        public void onSure() {
                            netAddAllocation(ids);
                        }
                    });
                }
                break;
            case R.id.text_sort_selectall:
                if (text_sort_selectall.isSelected()) {
                    text_sort_selectall.setSelected(false);
                    adapter.setSelectAll(false);
                } else {
                    text_sort_selectall.setSelected(true);
                    adapter.setSelectAll(true);
                }
                break;
        }
    }

    private void freshData(List<User> results) {
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
        if (!StrUtil.isEmpty(users)) {
            List<User> resultsSort = new ArrayList<>();
            for (User sortBean : users) {
                if (SortUtil.match(sortBean, filterStr)) {
                    resultsSort.add(sortBean);
                }
            }
            freshData(resultsSort);
        }
    }

    //给Edit设置Hint文字（带图片）
    private SpannableString makeSearchHint() {
        SpannableString spannableString = new SpannableString("1 搜索");
        Drawable drawable = getResources().getDrawable(R.drawable.ic_home_search_edit);
        drawable.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private void netQueryUserAlloc() {
        Map<String, Object> param = new NetParam()
                .put("curriculumId", lessonId)
                .put("isAllocation", 1)
                .put("pageNO", 1)
                .put("pageSize", 1000)
                .build();
        loadingLayout.showLoadingView();
        NetApi.NI().queryUserAlloc(param).enqueue(new BaseCallback<List<User>>(new TypeToken<List<User>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<User> users, String msg) {
                if (!StrUtil.isEmpty(users)) {
                    SortUserActivity.this.users = users;
                    convert(users);
                    freshData(users);
                    loadingLayout.showOut();
                } else {
                    loadingLayout.showLackView();
                }
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                loadingLayout.showOut();
            }
        });
    }

    private void convert(List<User> users) {
        for (User user : users) {
            user.setSortName(user.getShowName());
        }
    }

    private void netAddAllocation(final String ids) {
        Map<String, Object> param = new NetParam()
                .put("curriculumId", lessonId)
                .put("userIds", ids)
                .put("number", 1)
                .build();
        showLoadingDialog();
        NetApi.NI().addAllocation(param).enqueue(new BaseCallback<CommonBean>(CommonBean.class) {
            @Override
            public void onSuccess(int status, CommonBean com, String msg) {
                ToastUtil.showToastShort(msg, true);
                hideLoadingDialog();
                //post分配成功消息，附带分配人数（课程数）
                EventBean eventBean = new EventBean(EventBean.EVENT_USER_ALLOCAT);
                eventBean.put("count", ids.split(",").length);
                EventBus.getDefault().post(eventBean);
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                hideLoadingDialog();
            }
        });
    }
}
