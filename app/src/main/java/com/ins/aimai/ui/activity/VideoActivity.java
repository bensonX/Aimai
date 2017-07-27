package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.dl7.player.interfaces.OnProgressChageListener;
import com.dl7.player.media.IjkPlayerView;
import com.dl7.player.media.MediaPlayerParams;
import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.common.CheckPoint;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.BaseCallback;
import com.ins.aimai.net.NetApi;
import com.ins.aimai.net.NetFaceHelper;
import com.ins.aimai.net.NetHelper;
import com.ins.aimai.net.NetParam;
import com.ins.aimai.ui.adapter.PagerAdapterVideo;
import com.ins.aimai.ui.base.BaseVideoActivity;
import com.ins.aimai.ui.dialog.DialogSureAimai;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoActivity extends BaseVideoActivity implements IMediaPlayer.OnInfoListener, OnProgressChageListener, NetFaceHelper.OnFaceCheckCallback {

    private IjkPlayerView player;
    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterVideo adapterPager;

    private DialogSureAimai dialogSureNext;
    private DialogSureAimai dialogSureFace;

    private String[] titles = new String[]{"介绍", "目录", "讲义", "评论"};

    private int lessonId;
    private Lesson lesson;  //课程
    private Video video;    //选择的video
    private List<FaceRecord> faceRecords;   //选择的video的播放记录


    public static void start(Context context, int lessonId) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("lessonId", lessonId);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_VIDEO_SELECT_DIRECTORY) {
            //选择了播放的视频
            Video video = (Video) event.get("video");
            setVideo(video);
        } else if (event.getEvent() == EventBean.EVENT_CAMERA_RESULT) {
            //人脸识别相机回调
            String path = (String) event.get("path");
            NetFaceHelper.getInstance().init(this, path, video.getId(), player.getCurPosition()).netEyeCheck(this);
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
        dialogSureNext = new DialogSureAimai(this, "本课时已看完", "您可以选择开始考核该课时，或者观看下个课时", "观看下个课时", "开始考核");
        dialogSureFace = new DialogSureAimai(this, "身份验证", "我们需要验证您是否本人观看，点击'开始验证'将对您进行人脸识别，如果您取消了本次验证你讲无法继续观看后面的课程", "取消", "开始验证");
        dialogSureFace.setOnOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraActivity.start(VideoActivity.this);
            }
        });
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        player = (IjkPlayerView) findViewById(R.id.player);
        setIjkPlayerView(player);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterVideo(getSupportFragmentManager(), titles, lessonId);
        pager.setAdapter(adapterPager);
        pager.setOffscreenPageLimit(2);
        tab.setupWithViewPager(pager);

        player.setOnInfoListener(this);
        player.setOnProgressChageListener(this);
    }

    private void initData() {
        netQueryLessonDetail();
    }

    private void setData(Lesson lesson) {
        if (lesson != null) {
            //TODO:设置默认视频
            Video video = AppHelper.VideoPlay.getDefaultVideoByLesson(lesson);
            if (video != null) {
                EventBean eventBean = new EventBean(EventBean.EVENT_VIDEO_SELECT_DIRECTORY);
                eventBean.put("video", video);
                EventBus.getDefault().post(eventBean);
            }
        }
    }

    private void setVideo(final Video video) {
        if (video == null) return;
        //保存正在播放的视频实体
        this.video = video;
        if (AppHelper.VideoPlay.isVideoStatusFinish(video)) {
            //如果已经播放完成的视频，直接播放
            setPlayerData(video);
        } else {
            //否则，获取播放检查记录
            NetHelper.getInstance().netQueryFaceRecord(this, video.getId(), new NetHelper.OnFaceRecordCallback() {
                @Override
                public void onSuccess(List<FaceRecord> faceRecords) {
                    setPlayerData(video);
                }
            });
        }
    }

    private void setPlayerData(Video video) {
        //加载封面图
        GlideUtil.loadBlurImg(VideoActivity.this, player.mPlayerThumb, video.getCover());
        player.setTitle(video.getName());
        player.setSkipTip(1000 * 60 * 1);
        player.setNeedLimit(false);
        player.enableDanmaku();
        player.setDanmakuSource(getResources().openRawResource(R.raw.bili));
        player.setVideoSource(null, AppData.Url.getVideoUrl(video.getLowDefinition()), AppData.Url.getVideoUrl(video.getHighDefinition()), null, null);
        player.setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
    }

    //#################### 事件监听 ######################

    //播放器状态变化事件
    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int status, int extra) {
        //TODO:这里进行进度本地化保存
        Log.e("liao", status + ":" + extra);
        if (AppHelper.VideoPlay.isVideoStatusFinish(video)) return false;
        switch (status) {
            case MediaPlayerParams.STATE_COMPLETED:
                //播放完成
                AppHelper.VideoPlay.setVideoStatusFinish(video);
                NetHelper.getInstance().netAddVideoStatus(video.getId(), player.getCurPosition() / 1000, true);
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_VIDEO_FINISH));
                break;
            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:    //开始播放
                //case IMediaPlayer.MEDIA_INFO_BUFFERING_START:    //缓冲开始（拖动进度条）
            case MediaPlayerParams.STATE_PAUSED:    //暂停
            case MediaPlayerParams.STATE_PLAYING:   //播放中（继续）
                NetHelper.getInstance().netAddVideoStatus(video.getId(), player.getCurPosition() / 1000, false);
                break;
            default:
                break;
        }
        return false;
    }

    //播放器进度变化事件
    @Override
    public void onProgress(int progress, int duration) {
        L.e("progress", progress + "/" + duration);
        if (AppHelper.VideoPlay.isVideoStatusFinish(video)) return;
        float lv = (float) progress / (float) duration;
        if (AppHelper.VideoPlay.needCheckFace(faceRecords, lv)) {
            //TODO:需要验证，则暂停视频，弹出验证对话框，【同时设置播放器为不允许继续播放】
            player.pause();
            dialogSureFace.show();
        }
    }

    @Override
    public void onFaceCheckSuccess() {
        dialogSureFace.hide();
        player.start();
    }

    @Override
    public void onFaceCheckFailed() {
        ToastUtil.showToastShort("身份验证不通过，您无法继续观看");
    }

    ///////////////////////////////

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

    //###################  get & set ###############


    public void setFaceRecords(List<FaceRecord> faceRecords) {
        this.faceRecords = faceRecords;
    }

    public List<FaceRecord> getFaceRecords() {
        return faceRecords;
    }
}
