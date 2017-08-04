package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.activity.LessonEmployActivity;
import com.ins.aimai.ui.adapter.RecycleAdapterLessonEmploy;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;

import java.util.List;

/**
 * Created by liaoinstan
 */
public class LessonEmployFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private View showin;
    private ViewGroup showingroup;

    private RecyclerView recycler;
    private RecycleAdapterLessonEmploy adapter;
    private LessonEmployActivity activity;

    public static Fragment newInstance(int position) {
        LessonEmployFragment fragment = new LessonEmployFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lesson_employ, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        activity = (LessonEmployActivity) getActivity();
    }

    private void initView() {
        showingroup = (ViewGroup) rootView.findViewById(R.id.showingroup);
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterLessonEmploy(getContext());
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 5, GridLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void initData() {
        if (position == 0) {
            setData(activity.getLesson().getWatchUsers());
        } else {
            setData(activity.getLesson().getSafeUsers());
        }
    }

    private void setData(List<User> users) {
        if (!StrUtil.isEmpty(users)) {
            adapter.getResults().clear();
            adapter.getResults().addAll(users);
            adapter.notifyDataSetChanged();
        }
        activity.setTabTextCount(position, adapter.getResults().size());
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
    }
}
