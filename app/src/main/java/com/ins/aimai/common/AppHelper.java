package com.ins.aimai.common;

import android.content.Context;

import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.NumUtil;

/**
 * Created by Administrator on 2017/7/24.
 */

public class AppHelper {

    public static String formatPrice(double ksum) {
        return NumUtil.num2half(ksum, 2);
    }

    public static void showLoadingDialog(Context context) {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).showLoadingDialog();
        }
    }

    public static void hideLoadingDialog(Context context) {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).hideLoadingDialog();
        }
    }

    public static class VideoPlay {

        //设置video为已播放状态
        public static void setVideoStatusFinish(Video video) {
            if (video != null) {
                if (video.getVideoStatus() == null) {
                    video.setVideoStatus(new VideoStatus());
                }
                video.getVideoStatus().setStatus(2);
            }
        }

        //检查一个Video是否已经播放完成
        public static boolean isVideoStatusFinish(Video video) {
            if (video.getVideoStatus() != null && video.getVideoStatus().getStatus() == 2) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static class OrderHelp {
        public static String getOrderStatusName(int status) {
            switch (status) {
                case 0:
                    return "未付款";
                case 1:
                    return "已完成";
                default:
                    return "";
            }
        }
    }
}
