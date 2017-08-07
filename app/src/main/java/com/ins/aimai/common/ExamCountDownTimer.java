package com.ins.aimai.common;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.ins.aimai.bean.common.EventBean;
import com.ins.common.utils.L;
import com.ins.common.utils.NumUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/8/7.
 */

public class ExamCountDownTimer {

    //爱麦用，获取已用时长，使用静态变量存储
    public static int useTime;

    private CountDownTimer timer;
    //记录生一次计时到什么地方
    private int lastTime;
    private int allTime;

    public ExamCountDownTimer(int allTime) {
        this.allTime = allTime;
        this.lastTime = allTime;
    }

    public void start() {
        timer = new MyCountDownTimer(lastTime * 1000, 1000);
        timer.start();
    }

    public void cancel() {
        timer.cancel();
    }

    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            lastTime = NumUtil.long2int(millisUntilFinished / 1000);
            useTime = allTime - lastTime;
            EventBean eventBean = new EventBean(EventBean.EVENT_EXAM_TIME);
            eventBean.put("time", (int) (millisUntilFinished / 1000));
            EventBus.getDefault().post(eventBean);
        }

        @Override
        public void onFinish() {
            onTick(0);
            EventBus.getDefault().post(new EventBean(EventBean.EVENT_EXAM_TIMEOUT));
        }
    }
}
