package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl7.player.interfaces.OnProgressChageListener;
import com.dl7.player.media.IjkPlayerView;
import com.dl7.player.media.MediaPlayerParams;
import com.ins.aimai.R;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.Order;
import com.ins.aimai.bean.Study;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.bean.common.VideoFinishStatus;
import com.ins.aimai.common.AppData;
import com.ins.aimai.common.AppHelper;
import com.ins.aimai.net.helper.NetFaceHelper;
import com.ins.aimai.net.helper.NetHelper;
import com.ins.aimai.ui.adapter.PagerAdapterVideo;
import com.ins.aimai.ui.base.BaseVideoActivity;
import com.ins.aimai.ui.dialog.DialogSureAimai;
import com.ins.aimai.ui.dialog.DialogToExam;
import com.ins.aimai.ui.view.TextTabLayout;
import com.ins.aimai.utils.ToastUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.PermissionsUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.others.CommonUtil;
import com.liaoinstan.springview.listener.AppBarStateChangeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;

//type: 0:使用lessonId进入 1:使用orderId进入
public class VideoActivity extends BaseVideoActivity implements IMediaPlayer.OnInfoListener, OnProgressChageListener, NetFaceHelper.OnFaceCompareCallback, View.OnClickListener {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appbar;

    private ImageView btn_right;
    private IjkPlayerView player;
    private TextTabLayout tab;
    private ViewPager pager;
    private PagerAdapterVideo adapterPager;

    private DialogToExam dialogToExam;
    private DialogSureAimai dialogSureFace;

    private String[] titles = new String[]{"介绍", "目录", "讲义", "评论"};

    private boolean needFreshTest;  //是否需要刷新学习考题统计（新的需要要求从课程学习页面进入视频播放页后同时刷新考题统计）
    private int lessonId;
    private int orderId;
    private Lesson lesson;  //课程
    private Video video;    //选择的video
    private List<FaceRecord> faceRecords;   //选择的video的播放记录
    private int type;
    //是否自动开始播放
    private boolean autoPlay = false;

    public static void startByLesson(Context context, int lessonId) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("lessonId", lessonId);
        intent.putExtra("type", 0);
        context.startActivity(intent);
    }

    public static void startByOrder(Context context, Study study) {
        startByOrder(context, study.getOrderId(), study.getId(), true);
    }

    public static void startByOrder(Context context, Order order) {
        startByOrder(context, order.getId(), order.getCurriculumId(), false);
    }

    private static void startByOrder(Context context, int orderId, int lessonId, boolean needFreshTest) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("lessonId", lessonId);
        intent.putExtra("needFreshTest", needFreshTest);
        intent.putExtra("type", 1);
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
            showLoadingDialog();
            dialogSureFace.hide();
            NetFaceHelper.getInstance().initCompare(this, path, orderId, video.getId(), player.getCurPosition() / 1000).netEyeCheck(this);
        } else if (event.getEvent() == EventBean.EVENT_VIDEO_FINISH_STATUS) {
            //视频播放完成更新状态回调
            VideoFinishStatus videoFinishStatus = (VideoFinishStatus) event.get("videoFinishStatus");
            if (!videoFinishStatus.isAllHide()) {
                //如果有试题就提示去做题
                boolean hasNextVideo = AppHelper.VideoPlay.hasNextVideo(lesson, video);
                dialogToExam.setData(videoFinishStatus, hasNextVideo);
                dialogToExam.show();
            } else {
                //否则直接播放下一个视频
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_VIDEO_START_NEXT));
            }
        } else if (event.getEvent() == EventBean.EVENT_VIDEO_START_NEXT) {
            autoPlay = true;
        } else if (event.getEvent() == EventBean.EVENT_VIDEO_TEXISIZE) {
            int sizeType = (int) event.get("sizeType");
            setTextSize(sizeType);
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
        StatusBarTextUtil.StatusBarLightMode(this);
        initBase();
        initView();
        initCtrl();
        initData();
    }

    @Override
    public void finish() {
        super.finish();
        if (needFreshTest) EventBus.getDefault().post(new EventBean(EventBean.EVENT_FRESHTEST));
    }

    private void initBase() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (getIntent().hasExtra("lessonId")) {
            lessonId = getIntent().getIntExtra("lessonId", 0);
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getIntExtra("orderId", 0);
        }
        if (getIntent().hasExtra("needFreshTest")) {
            needFreshTest = getIntent().getBooleanExtra("needFreshTest", false);
        }
        dialogToExam = new DialogToExam(this);
        dialogSureFace = new DialogSureAimai(this, "身份验证", "我们需要验证您是否本人观看，点击‘开始验证’将对您进行人脸识别，如果您取消了本次验证将讲无法继续观看后面的课程", "取消", "开始验证");
        dialogSureFace.setOnOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionsUtil.checkCamera(VideoActivity.this)) {
                    CameraActivity.start(VideoActivity.this);
                    dialogSureFace.hide();
                }
            }
        });
    }

    private void initView() {
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        btn_right = (ImageView) findViewById(R.id.btn_right);
        tab = (TextTabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        player = (IjkPlayerView) findViewById(R.id.player);
        setIjkPlayerView(player);
        btn_right.setOnClickListener(this);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterVideo(getSupportFragmentManager(), titles, lessonId);
        pager.setAdapter(adapterPager);
        pager.setOffscreenPageLimit(2);
        tab.setupWithViewPager(pager);

        player.setOnInfoListener(this);
        player.setOnProgressChageListener(this);
        //设置开始播放拦截（如果没有视频则忽略点击事件并提示）
        player.setVideoStartIntercept(new IjkPlayerView.VideoStartIntercept() {
            @Override
            public boolean onVideoStart() {
                if (AppHelper.VideoPlay.checkHasVideo(lesson)) {
                    return false;
                } else {
                    ToastUtil.showToastShort("该课程还没有添加视频");
                    return true;
                }
            }
        });
        //恢复上次选择的字体大小
        int sizeType = AppData.App.getTextSizeVideo();
        EventBean eventBean = new EventBean(EventBean.EVENT_VIDEO_TEXISIZE);
        eventBean.put("sizeType", sizeType);
        EventBus.getDefault().post(eventBean);
    }

    private void initData() {
        netQueryLessonDetail();
    }

    private void setData(Lesson lesson) {
        if (lesson != null) {
        }
    }

    private void setVideo(final Video video) {
        if (video == null) return;
        setToolbar(video.getName());
        //保存正在播放的视频实体
        this.video = video;
        if (AppHelper.VideoPlay.isVideoFreeCtrl(video, type)) {
            //如果已经播放完成的视频，直接播放
            setPlayerData(video);
        } else {
            //否则，获取播放检查记录
            NetHelper.getInstance().netQueryFaceRecord(this, orderId, video.getId(), new NetHelper.OnFaceRecordCallback() {
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
        //player.setSkipTip(1000 * 60 * 1);
        //player.enableDanmaku();
        //player.setDanmakuSource(getResources().openRawResource(R.raw.bili));
        player.setVideoSource(null, AppData.Url.getVideoUrl(video.getLowDefinition()), AppData.Url.getVideoUrl(video.getHighDefinition()), null, null);
        player.setMediaQuality(AppHelper.UserHelp.isHighDefinition() ? IjkPlayerView.MEDIA_QUALITY_HIGH : IjkPlayerView.MEDIA_QUALITY_MEDIUM);
//        player.setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
        if (!AppHelper.VideoPlay.isVideoFreeCtrl(video, type)) {
            VideoStatus videoStatus = video.getVideoStatus();
            player.setNeedLimit(true);
            if (videoStatus != null)
                player.setSaveLimitTime(videoStatus.getSeconds() * 1000);
        } else {
            player.setNeedLimit(false);
        }
        //是否自动开始
        if (autoPlay) player.start();
        autoPlay = false;
    }

    private void setTextSize(int sizeType) {
        switch (sizeType) {
            case AppData.Constant.TEXTSIZE_BIG:
                tab.setTextSize(15);
                break;
            case AppData.Constant.TEXTSIZE_MIDDLE:
                tab.setTextSize(14);
                break;
            case AppData.Constant.TEXTSIZE_SMALL:
                tab.setTextSize(13);
                break;
        }
    }

    //#################### 事件监听 ######################

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                EventBean eventBean = new EventBean(EventBean.EVENT_VIDEO_TEXISIZE);
                int sizeType = AppData.App.getTextSizeVideo();
                if (sizeType == AppData.Constant.TEXTSIZE_BIG) {
                    eventBean.put("sizeType", AppData.Constant.TEXTSIZE_MIDDLE);
                    AppData.App.saveTextSizeVideo(AppData.Constant.TEXTSIZE_MIDDLE);
                } else if (sizeType == AppData.Constant.TEXTSIZE_MIDDLE) {
                    eventBean.put("sizeType", AppData.Constant.TEXTSIZE_SMALL);
                    AppData.App.saveTextSizeVideo(AppData.Constant.TEXTSIZE_SMALL);
                } else if (sizeType == AppData.Constant.TEXTSIZE_SMALL) {
                    eventBean.put("sizeType", AppData.Constant.TEXTSIZE_BIG);
                    AppData.App.saveTextSizeVideo(AppData.Constant.TEXTSIZE_BIG);
                }
                EventBus.getDefault().post(eventBean);
                break;
        }
    }

    //播放器状态变化事件
    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int status, int extra) {
        //TODO:这里进行进度本地化保存
        Log.e("liao", status + ":" + extra);
        if (!AppHelper.VideoPlay.isVideoFreeCtrl(video, type)) {
            switch (status) {
                case MediaPlayerParams.STATE_COMPLETED:
                    //播放完成
                    AppHelper.VideoPlay.setVideoStatusFinish(video);
                    NetHelper.getInstance().netAddVideoStatus(video.getVideoStatus(), orderId, video.getId(), player.getCurPosition() / 1000, true);
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_VIDEO_FINISH));
                    player.stop();
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:    //开始播放
                    NetHelper.getInstance().netAddVideoStatus(video.getVideoStatus(), orderId, video.getId(), player.getCurPosition() / 1000, false);
                    break;
                case MediaPlayerParams.STATE_PAUSED:    //暂停
                    CommonUtil.setEnableCollapsing(collapsingToolbarLayout, true);
                    NetHelper.getInstance().netAddVideoStatus(video.getVideoStatus(), orderId, video.getId(), player.getCurPosition() / 1000, false);
                    break;
//                case MediaPlayerParams.STATE_PLAYING:   //播放中（继续）
//                    CommonUtil.setEnableCollapsing(collapsingToolbarLayout, false);
//                    NetHelper.getInstance().netAddVideoStatus(video.getVideoStatus(), orderId, video.getId(), player.getCurPosition() / 1000, false);
//                    break;
                default:
                    break;
            }
        } else {
            switch (status) {
                case MediaPlayerParams.STATE_COMPLETED:     //播放完成
                    player.stop();
                    //开始下个视频
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_VIDEO_START_NEXT));
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:    //开始播放
                    break;
                case MediaPlayerParams.STATE_PAUSED:    //暂停
                    CommonUtil.setEnableCollapsing(collapsingToolbarLayout, true);
                    break;
                case MediaPlayerParams.STATE_PLAYING:   //播放中（继续）
                    CommonUtil.setEnableCollapsing(collapsingToolbarLayout, false);
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    //播放器进度变化事件
    @Override
    public void onProgress(int progress, int duration) {
        if (AppHelper.VideoPlay.isVideoFreeCtrl(video, type)) return;
        float lv = (float) progress / (float) duration;
        float addLv = (float) 60 / (float) (duration/1000);
        if (AppHelper.VideoPlay.needCheckFace(faceRecords, lv, addLv)) {
            if (player.isFullScreen()) {
                player.setFullScreen(false);
            }
            player.pause();
            dialogSureFace.show();
        }
    }

    //人脸验证通过
    @Override
    public void onFaceCompareSuccess() {
        hideLoadingDialog();
        dialogSureFace.hide();
        player.start();
    }

    //人脸颜色不通过
    @Override
    public void onFaceCompareFailed() {
        hideLoadingDialog();
        dialogSureFace.show();
        ToastUtil.showToastShort("身份验证不通过，您无法继续观看");
    }
    ///////////////////////////////

    //把数据post给介绍页
    private void postIntro(Lesson lesson) {
        EventBean eventBean = new EventBean(EventBean.EVENT_LESSONDETAIL_INTRO);
        eventBean.put("intro", lesson.getCurriculumDescribe());
        eventBean.put("teacherName", lesson.getTeacherName());
        eventBean.put("teacherIntro", lesson.getTeacherIntroduce());
        eventBean.put("applyPerson", lesson.getApplyPerson());
        EventBus.getDefault().post(eventBean);
    }

    //把数据post给目录
    private void postDirectory(List<CourseWare> courseWares) {
        if (!StrUtil.isEmpty(courseWares)) {
            EventBean eventBean = new EventBean(EventBean.EVENT_LESSONDETAIL_DIRECTORY);
            eventBean.put("courseWares", courseWares);
            EventBus.getDefault().post(eventBean);
        }
    }

    private void netQueryLessonDetail() {
        NetHelper.getInstance().netQueryLessonDetail(type, lessonId, orderId, new NetHelper.OnLessonCallback() {
            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onSuccess(int status, Lesson lesson, String msg) {
                VideoActivity.this.lesson = lesson;
                convert(lesson);
                setData(lesson);
                postIntro(lesson);
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

    //把每个Video的videoStatus设置为不为空
    private void convert(Lesson lesson) {
        if (lesson == null || StrUtil.isEmpty(lesson.getCourseWares())) return;
        for (CourseWare courseWare : lesson.getCourseWares()) {
            if (!StrUtil.isEmpty(courseWare.getVideos())) {
                for (Video video : courseWare.getVideos()) {
                    if (video.getVideoStatus() == null) {
                        video.setVideoStatus(new VideoStatus());
                    }
                }
            }
        }
    }

    //###################  get & set ###############


    public void setFaceRecords(List<FaceRecord> faceRecords) {
        this.faceRecords = faceRecords;
    }

    public List<FaceRecord> getFaceRecords() {
        return faceRecords;
    }

    public AppBarLayout getAppbar() {
        return appbar;
    }
}
