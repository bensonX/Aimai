package com.ins.aimai.common;

import android.content.Context;

import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.bean.common.CheckPoint;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.common.utils.NumUtil;
import com.ins.common.utils.StrUtil;

import java.util.ArrayList;
import java.util.List;

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
            if (video == null) return false;
            if (video.getVideoStatus() != null && video.getVideoStatus().getStatus() == 2) {
                return true;
            } else {
                return false;
            }
        }

        //检查一个Video是否是自由控制
        public static boolean isVideoFreeCtrl(Video video, int type) {
            if (type == 1) {
                return isVideoStatusFinish(video);
            } else {
                return true;
            }
        }

        //根据播放检查记录判断当前位置是否需要进行视频检查
        public static boolean needCheckFace(List<FaceRecord> faceRecords, float lv) {
            for (CheckPoint checkPoint : AppData.App.getVideoCheckPoint()) {
                if (lv >= checkPoint.getCheckValue()) {
                    int checkIndex = checkPoint.getIndex();
                    int recordsSize = faceRecords.size();
                    /**
                     * checkIndex  recordsSize
                     *    0           0
                     *    1           0,1
                     *    2           0,1,2
                     * checkIndex >= recordsSize
                     */
                    if (checkIndex >= recordsSize) {
                        return true;
                    }
                }
            }
            return false;
        }

        //把课件集合中的视频按列表形式返回
        public static List<Video> convertVideosByCourseWares(List<CourseWare> courseWares) {
            ArrayList<Video> videos = new ArrayList<>();
            for (CourseWare courseWare : courseWares) {
                if (!StrUtil.isEmpty(courseWare.getVideos())) {
                    for (Video video : courseWare.getVideos()) {
                        video.setCourseWareName(courseWare.getCourseWareName());
                        video.setPpt(courseWare.getPpt());
                        videos.add(video);
                    }
                }
            }
            return videos;
        }

        //检查一个Video是否已经播放完成
        public static Video getDefaultVideoByLesson(Lesson lesson) {
            if (lesson == null || StrUtil.isEmpty(lesson.getCourseWares()))
                return null;
            List<Video> videos = convertVideosByCourseWares(lesson.getCourseWares());
            //返回第一个未播放完的视频，如果都播放完了，返回最后一个,否则返回null
            if (StrUtil.isEmpty(videos)) return null;
            for (Video video : videos) {
                if (!isVideoStatusFinish(video)) {
                    return video;
                }
            }
            return videos.get(videos.size() - 1);
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
