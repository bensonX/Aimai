package com.ins.aimai.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.bean.common.VideoDirectiry;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.ui.adapter.RecycleAdapterVideoDirectory;
import com.ins.aimai.ui.base.BaseFragment;
import com.ins.aimai.ui.base.BaseVideoActivity;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan
 */
public class VideoDirectotyFragment extends BaseFragment implements OnRecycleItemClickListener {

    private int position;
    private View rootView;

    private RecyclerView recycler;
    private RecycleAdapterVideoDirectory adapter;

    //当前正在播放的视频
    private Video video;

    public static Fragment newInstance(int position) {
        VideoDirectotyFragment fragment = new VideoDirectotyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_LESSONDETAIL_DIRECTORY) {
            //获取到课程列表数据集
            List<CourseWare> courseWares = (List<CourseWare>) event.get("courseWares");
            freshData(AppHelper.VideoPlay.convertVideosByCourseWares(courseWares));
            playDefault();
        } else if (event.getEvent() == EventBean.EVENT_VIDEO_FINISH) {
            //视频播放完成，这里要刷新播放列表
            adapter.notifyDataSetChanged();
        } else if (event.getEvent() == EventBean.EVENT_VIDEO_START_NEXT) {
            //开始播放下一个视频
            playNext();
        } else if (event.getEvent() == EventBean.EVENT_VIDEO_TEXISIZE) {
            int sizeType = (int) event.get("sizeType");
            setTextSize(sizeType);
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
        rootView = inflater.inflate(R.layout.fragment_videodirectory, container, false);
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
        adapter = new RecycleAdapterVideoDirectory(getContext());
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    private void initData() {
    }

    private void freshData(List<Video> directiries) {
        adapter.getResults().clear();
        adapter.getResults().addAll(directiries);
        adapter.notifyDataSetChanged();
    }

    private void setTextSize(int sizeType) {
        adapter.setTextSize(sizeType);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
        Video video = adapter.getResults().get(viewHolder.getLayoutPosition());
        playVideo(video);
    }

    private void playVideo(Video video) {
        this.video = video;
        if (getActivity() instanceof BaseVideoActivity && video != null) {
            EventBean eventBean = new EventBean(EventBean.EVENT_VIDEO_SELECT_DIRECTORY);
            eventBean.put("video", video);
            EventBus.getDefault().post(eventBean);
        }
    }

    private void playNext() {
        Video defaultVideo = AppHelper.VideoPlay.getNextVideo(adapter.getResults(), video);
        playVideo(defaultVideo);
    }

    private void playDefault() {
        Video defaultVideo = AppHelper.VideoPlay.getDefaultVideo(adapter.getResults());
        playVideo(defaultVideo);
    }
}
