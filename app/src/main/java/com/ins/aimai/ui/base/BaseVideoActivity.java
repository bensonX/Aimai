package com.ins.aimai.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import com.dl7.player.media.IjkPlayerView;

/**
 * Created by Administrator on 2017/7/24.
 */

public class BaseVideoActivity extends BaseAppCompatActivity{

    private IjkPlayerView ijkPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ijkPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkPlayerView.onDestroy();
    }

    //旋转屏幕后播放器要处理先后旋转的样式
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ijkPlayerView.configurationChanged(newConfig);
    }

    //处理音量键，避免外部按音量键后导航栏和状态栏显示出来退不回去的状态（优先处理播放器音量键事件）
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ijkPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //回退，全屏时退回竖屏 (优先处理播放器回退事件)
    @Override
    public void onBackPressed() {
        if (ijkPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    public void setIjkPlayerView(IjkPlayerView ijkPlayerView){
        this.ijkPlayerView = ijkPlayerView;
        //初始化播放器
        ijkPlayerView.init();
    }
}
