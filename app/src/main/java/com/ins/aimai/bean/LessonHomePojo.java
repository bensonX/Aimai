package com.ins.aimai.bean;

import com.google.gson.annotations.SerializedName;
import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class LessonHomePojo implements Serializable {

    @SerializedName("curriculumTypes")
    private List<LessonCate> lessonCates;

    @SerializedName("recommendCurriculumList")
    private List<Lesson> recommendLessons;

    @SerializedName("curriculumByAudition")
    private List<Lesson> freeLessons;

    ////////////// 业务方法 //////////////

    //把推荐课程也当做一个类别
    //如果类别中没有课程，那么移除该项
    public void convert() {
        LessonCate lessonCate = new LessonCate(-1, "推荐课程", recommendLessons);
        lessonCates.add(0, lessonCate);
        Iterator<LessonCate> iterator = lessonCates.iterator();
        while (iterator.hasNext()) {
            LessonCate cate = iterator.next();
            if (StrUtil.isEmpty(cate.getLessons())) {
                lessonCates.remove(cate);
            }
        }
    }

    ////////////// get & set //////////////

    public List<LessonCate> getLessonCates() {
        return lessonCates;
    }

    public void setLessonCates(List<LessonCate> lessonCates) {
        this.lessonCates = lessonCates;
    }

    public List<Lesson> getRecommendLessons() {
        return recommendLessons;
    }

    public void setRecommendLessons(List<Lesson> recommendLessons) {
        this.recommendLessons = recommendLessons;
    }

    public List<Lesson> getFreeLessons() {
        return freeLessons;
    }

    public void setFreeLessons(List<Lesson> freeLessons) {
        this.freeLessons = freeLessons;
    }
}
