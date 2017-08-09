package com.ins.aimai.common;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ins.aimai.bean.CourseWare;
import com.ins.aimai.bean.Examination;
import com.ins.aimai.bean.ExaminationItems;
import com.ins.aimai.bean.Lesson;
import com.ins.aimai.bean.User;
import com.ins.aimai.bean.Video;
import com.ins.aimai.bean.VideoStatus;
import com.ins.aimai.bean.common.CheckPoint;
import com.ins.aimai.bean.common.ExamSubmitBean;
import com.ins.aimai.bean.common.FaceRecord;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.ui.base.BaseAppCompatActivity;
import com.ins.aimai.ui.view.QuestionView;
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

    public static String formatPercent(double percent) {
        return NumUtil.num2half(percent, 2) + "%";
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

    //判断当前登录用户是否为普通用户，未登录返回否
    public static boolean isUser() {
        User user = AppData.App.getUser();
        if (user != null && user.isUser()) {
            return true;
        } else {
            return false;
        }
    }

    //判断当前登录用户是否为政府用户，未登录返回否
    public static boolean isGov() {
        User user = AppData.App.getUser();
        if (user != null && user.isGovUser()) {
            return true;
        } else {
            return false;
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

    public static class Exam {

        public static List<QuestionBean> transExamination2QuestionBeans(List<Examination> examinations) {
            List<QuestionBean> questions = new ArrayList<>();
            if (!StrUtil.isEmpty(examinations)) {
                for (Examination exam : examinations) {
                    QuestionBean questionBean = new QuestionBean();
                    questionBean.setId(exam.getId());           //id
                    questionBean.setTitle(exam.getTitle());     //题目
                    questionBean.setType(exam.getType());       //类型
                    questionBean.setTypeName(exam.getType() == 0 ? "单选题" : (exam.getType() == 1 ? "多选题" : "判断题"));  //类型的文本描述
                    questionBean.setOptionBeans(transExaminationItem2Potions(exam.getExaminationItemsList(), exam.getType()));  //选项实体
                    questionBean.setAnalysis(exam.getExaminationKey()); //解析
                    questionBean.setPoint(exam.getEmphasis());          //考点
                    questions.add(questionBean);
                }
            }
            return questions;
        }

        private static List<QuestionView.Option> transExaminationItem2Potions(List<ExaminationItems> itemses, int type) {
            List<QuestionView.Option> options = new ArrayList<>();
            if (!StrUtil.isEmpty(itemses)) {
                for (int i = 0; i < itemses.size(); i++) {
                    ExaminationItems item = itemses.get(i);
                    QuestionView.Option option = new QuestionView.Option(item.getItemTitle());
                    option.id = item.getId();
                    option.index = i;
                    option.isCorrect = item.getIsCorrect() == 1 ? true : false;
                    options.add(option);
                }
                //如果是判断题加一个正确或者错误选项（服务器没有加，移动端帮忙处理）
                if (type == 2) {
                    ExaminationItems lastItem = itemses.get(0);
                    QuestionView.Option option = new QuestionView.Option(lastItem.getExaminationName().contains("正确") ? "错误" : "正确");
                    options.add(option);
                }
            }
            return options;
        }

        private static List<Integer> getAnswerIdsList(QuestionBean question) {
            ArrayList<Integer> ids = new ArrayList<>();
            if (question != null && !StrUtil.isEmpty(question.getOptionBeans())) {
                for (QuestionView.Option option : question.getOptionBeans()) {
                    if (option.isSelect()) {
                        ids.add(option.id);
                    }
                }
            }
            return ids;
        }

        public static String getAnswerSubmitParam(List<QuestionBean> questions) {
            if (StrUtil.isEmpty(questions)) {
                return "";
            }
            ArrayList<ExamSubmitBean> submits = new ArrayList<>();
            for (QuestionBean question : questions) {
                ExamSubmitBean submit = new ExamSubmitBean();
                submit.setExaminationId(question.getId());
                submit.setAnswers(getAnswerIdsList(question));
                submits.add(submit);
            }
            return new Gson().toJson(submits);
        }

        //是否已经做完所有题了
        public static boolean checkIsFinishQuestions(List<QuestionBean> questions) {
            if (StrUtil.isEmpty(questions)) {
                return true;
            }
            for (QuestionBean question : questions) {
                if (!question.isChoosed()) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class Gov {
        public static String getCompAddressStr(User comp) {
            if (comp == null || comp.getCity() == null) {
                return "";
            }
            String mergerName = comp.getCity().getMergerName();
            String distic = comp.getCity().getShortName();
            String[] split = mergerName.split(",");
            if (!StrUtil.isEmpty(split) && split.length == 4) {
                String city = split[2];
                city = StrUtil.subLastChart(city, "市");
                return city + "·" + distic;
            } else {
                return mergerName;
            }
        }
    }

    public static class Comment {
        public static String getShadowName(String name) {
            if (TextUtils.isEmpty(name)) {
                return "";
            } else if (name.length() == 1) {
                return name.substring(0, 1);
            } else if (name.length() == 2) {
                return name.substring(0, 1) + "*";
            } else {
                String startName = name.substring(0, 1);
                String endName = name.substring(name.length() - 1);
                String shadow = "";
                for (int i = 0; i < name.length() - 2; i++) {
                    shadow += "*";
                }
                return startName + shadow + endName;
            }
        }
    }
}
