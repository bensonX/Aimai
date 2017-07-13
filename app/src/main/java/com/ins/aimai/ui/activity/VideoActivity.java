package com.ins.aimai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.PagerAdapterVideo;
import com.ins.aimai.ui.adapter.RecycleAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.dialog.DialogSureAimai;
import com.ins.common.utils.App;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import jp.wasabeef.glide.transformations.BlurTransformation;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoActivity extends BaseAppCompatActivity {

    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";
    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/HD/movie_index.m3u8";
    private static final String IMAGE_URL = "http://vimg2.ws.126.net/image/snapshot/2016/11/I/M/VC62HMUIM.jpg";

    private IjkPlayerView player;
    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterVideo adapterPager;

    private DialogSureAimai dialogSureAimai;

    private String[] titles = new String[]{"介绍", "目录", "讲义", "评论"};

    public static void start(Context context) {
        Intent intent = new Intent(context, VideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setToolbar();
        toolbar.bringToFront();
        StatusBarTextUtil.transparencyBar(this);
        initBase();
        initView();
        initCtrl();
        initData();
        dialogSureAimai.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.onDestroy();
    }

    private void initBase() {
        dialogSureAimai = new DialogSureAimai(this, "本课时已看完", "您可以选择开始考核该课时，或者观看下个课时", "观看下个课时", "开始考核");
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        pager = (ViewPager) findViewById(R.id.pager);
        player = (IjkPlayerView) findViewById(R.id.player);
    }

    private void initCtrl() {
        adapterPager = new PagerAdapterVideo(getSupportFragmentManager(), titles);
        pager.setAdapter(adapterPager);
        tab.setupWithViewPager(pager);

        //播放器初始化
        GlideUtil.loadBlurImg(this, player.mPlayerThumb, IMAGE_URL);
        player.mPlayerThumb.setImageResource(R.mipmap.ic_launcher);
        player.init()
                .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
                .setSkipTip(1000 * 60 * 1)
                .enableDanmaku()
                .setDanmakuSource(getResources().openRawResource(R.raw.bili))
                .setVideoSource(null, VIDEO_URL, VIDEO_HD_URL, null, null)
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
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
    }


    //旋转屏幕后播放器要处理先后旋转的样式
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        player.configurationChanged(newConfig);
    }

    //处理音量键，避免外部按音量键后导航栏和状态栏显示出来退不回去的状态（优先处理播放器音量键事件）
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (player.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //回退，全屏时退回竖屏 (优先处理播放器回退事件)
    @Override
    public void onBackPressed() {
        if (player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
