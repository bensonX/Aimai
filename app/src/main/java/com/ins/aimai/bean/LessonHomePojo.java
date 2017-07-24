package com.ins.aimai.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
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

    public void convert() {
        LessonCate lessonCate = new LessonCate(-1, "推荐课程", recommendLessons);
        lessonCates.add(0, lessonCate);
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
