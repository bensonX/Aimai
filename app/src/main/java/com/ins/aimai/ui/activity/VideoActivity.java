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

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.PagerAdapterVideo;
import com.ins.aimai.ui.adapter.RecycleAdapterLesson;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.StatusBarTextUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoActivity extends BaseAppCompatActivity {

    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";
    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/HD/movie_index.m3u8";
    private static final String IMAGE_URL = "http://vimg2.ws.126.net/image/snapshot/2016/11/I/M/VC62HMUIM.jpg";

    private IjkPlayerView player;
    private TabLayout tab;
    private ViewPager pager;
    private PagerAdapterVideo adapterPager;

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
    //旋转屏幕后播放器要处理先后旋转的样式
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        player.configurationChanged(newConfig);
    }

    private void initBase() {
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
        Glide.with(this).load(IMAGE_URL).fitCenter().into(player.mPlayerThumb);
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
}
