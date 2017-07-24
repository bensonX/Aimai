package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;

import com.dl7.player.media.IjkPlayerView;
import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.PagerAdapterVideo;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.base.BaseVideoActivity;
import com.ins.aimai.ui.dialog.DialogSureAimai;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoActivity extends BaseVideoActivity {

    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";
    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/HD/movie_index.m3u8";
    private static final String IMAGE_URL = "http://vimg2.ws.126.net/image/snapshot/2016/11/I/M/VC62HMUIM.jpg";

    private IjkPlayerView player;
    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterVideo adapterPager;

    private DialogSureAimai dialogSureAimai;

    private String[] titles = new String[]{"介绍", "目录", "讲义", "评论"};

    private int lessonId;
    private Lesson lesson;


    public static void start(Context context, int lessonId) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("lessonId", lessonId);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_VIDEO_SELECT_DIRECTORY) {
            Video video = (Video) event.get("video");
            setVideo(video);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setToolbar();
        registEventBus();
        toolbar.bringToFront();
        StatusBarTextUtil.transparencyBar(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        if (getIntent().hasExtra("lessonId")) {
            lessonId = getIntent().getIntExtra("lessonId", 0);
        }
        dialogSureAimai = new DialogSureAimai(this, "本课时已看完", "您可以选择开始考核该课时，或者观看下个课时", "观看下个课时", "开始考核");
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        player = (IjkPlayerView) findViewById(R.id.player);
        setIjkPlayerView(player);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterVideo(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        pager.setOffscreenPageLimit(2);
        tab.setupWithViewPager(pager);

        player.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                //TODO:这里进行进度本地化保存，在每个状态都会回调，包括杀死进程
                Log.e("liao", i + " " + i1);
                return false;
            }
        });
    }

    private void initData() {
        netQueryLessonDetail();
    }

    private void setData(Lesson lesson) {
        if (lesson != null) {
        }
    }

    private void setVideo(Video video){
        //加载封面图
        GlideUtil.loadBlurImg(this, player.mPlayerThumb, video.getHighDefinition());
        player.setTitle(video.getName());
        player.setSkipTip(1000 * 60 * 1);
        player.setNeedLimit(false);
        player.enableDanmaku();
        player.setDanmakuSource(getResources().openRawResource(R.raw.bili));
        player.setVideoSource(null, VIDEO_URL, VIDEO_HD_URL, null, null);
        player.setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
    }


    private void postIntro(String intro) {
        EventBean eventBean = new EventBean(EventBean.EVENT_LESSONDETAIL_INTRO);
        eventBean.put("intro", intro);
        EventBus.getDefault().post(eventBean);
    }

    private void postDirectory(List<CourseWare> courseWares) {
        if (!StrUtil.isEmpty(courseWares)) {
            EventBean eventBean = new EventBean(EventBean.EVENT_LESSONDETAIL_DIRECTORY);
            eventBean.put("courseWares", courseWares);
            EventBus.getDefault().post(eventBean);
        }
    }

    private void netQueryLessonDetail() {
        Map<String, Object> param = new NetParam()
                .put("curriculumId", lessonId)
                .build();
        showLoadingDialog();
        NetApi.NI().queryLessonDetail(param).enqueue(new BaseCallback<Lesson>(Lesson.class) {
            @Override
            public void onSuccess(int status, Lesson lesson, String msg) {
                VideoActivity.this.lesson = lesson;
                setData(lesson);
                postIntro(lesson.getCurriculumDescribe());
                postDirectory(lesson.getCourseWares());
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
