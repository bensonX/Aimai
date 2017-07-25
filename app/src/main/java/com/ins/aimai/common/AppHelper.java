package com.ins.aimai.common;

import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.VideoStatus;
import com.ins.common.utils.NumUtil;

/**
 * Created by Administrator on 2017/7/24.
 */

public class AppHelper {

    public static String formatPrice(double ksum) {
        return NumUtil.num2half(ksum, 1);
    }

    public static class VideoPlay {

        public static void setVideoStatusFinish(Video video) {
            if (video != null) {
                if (video.getVideoStatus() == null) {
                    video.setVideoStatus(new VideoStatus());
                }
                video.getVideoStatus().setStatus(2);
            }
        }
    }
}
