package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.adapter.RecycleAdapterVideoNote;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.common.helper.LoadingViewHelper;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liaoinstan
 */
public class VideoNoteFragment extends BaseFragment {

    private int position;
    private View rootView;

    private RecyclerView recycler;
    private RecycleAdapterVideoNote adapter;

    public static Fragment newInstance(int position) {
        VideoNoteFragment fragment = new VideoNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_VIDEO_SELECT_DIRECTORY) {
            Video video = (Video) event.get("video");
            freshData(video);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_videonote, container, false);
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
    }

    private void initView() {
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
    }

    private void initCtrl() {
        adapter = new RecycleAdapterVideoNote(getContext());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    private void initData() {
    }

    private void freshData(Video video) {
        String pptstr = video.getPpt();
        if (!TextUtils.isEmpty(pptstr)) {
            String[] ppts = pptstr.split(",");
            List<String> results = Arrays.asList(ppts);
            adapter.getResults().clear();
            adapter.getResults().addAll(results);
            adapter.notifyDataSetChanged();
        }
    }
}
