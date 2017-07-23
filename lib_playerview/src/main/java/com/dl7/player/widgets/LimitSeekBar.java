package com.dl7.player.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by liaoinstan on 2017/7/3.
 * 这个SeekBar继承自系统SeekBar ，新增了临界值的功能：比如设置临界值：50，那么滑条滑动不会超过50
 * <p>
 * 注意：
 * 这个功能主要在AppCompatSeekBar.OnSeekBarChangeListener的监听中实现功能然后回调自己的同名监听接口LimitSeekBar.OnSeekBarChangeListener
 * 注意要使用setOnSeekBarChangeListener(LimitSeekBar.OnSeekBarChangeListener)代替setOnSeekBarChangeListener(AppCompatSeekBar.OnSeekBarChangeListener)
 */
//TODO:当前实现了每次设置进度为临界值，但是和真实需求并不吻合
//TODO:1.初始化进入界面如果有临界标志，则默认临界值0（不是-1）
//TODO:2.每次取得保存的最大临界值，而不是每次设置进度时更新
//TODO:手动保存临界值，并在之前进行判断if(保存的临界值和当前进度最大值)
//TODO:
//TODO:

public class LimitSeekBar extends AppCompatSeekBar {

    private Context context;
    private int limitProgress = -1; //临界值，如果为-1则表示失效

    public LimitSeekBar(Context context) {
        this(context, null);
    }

    public LimitSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.seekBarStyle);
    }

    public LimitSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (onSeekBarChangeListener != null) {
                    if (fromUser && progress >= limitProgress && limitProgress != -1) {
                        seekBar.setProgress(limitProgress);
                        onSeekBarChangeListener.onProgressChanged((LimitSeekBar) seekBar, limitProgress, fromUser);
                    } else {
                        onSeekBarChangeListener.onProgressChanged((LimitSeekBar) seekBar, progress, fromUser);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (onSeekBarChangeListener != null)
                    onSeekBarChangeListener.onStartTrackingTouch((LimitSeekBar) seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (onSeekBarChangeListener != null)
                    onSeekBarChangeListener.onStopTrackingTouch((LimitSeekBar) seekBar);
            }
        });
    }

    public synchronized void setProgress(int progress, int saveLimitProgress) {
        setProgress(progress);
        this.limitProgress = saveLimitProgress;
    }

    private OnSeekBarChangeListener onSeekBarChangeListener;

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }

    public interface OnSeekBarChangeListener {
        void onProgressChanged(LimitSeekBar seekBar, int progress, boolean fromUser);

        void onStartTrackingTouch(LimitSeekBar seekBar);

        void onStopTrackingTouch(LimitSeekBar seekBar);
    }

    ////////////// get & set ////////////

    public int getLimitProgress() {
        return limitProgress;
    }

    public void setLimitProgress(int limitProgress) {
        this.limitProgress = limitProgress;
    }
}