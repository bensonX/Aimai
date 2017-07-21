package com.ins.aimai.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class LessonCate implements Serializable{

    private int id;
    private String curriculumTypeName;
    @SerializedName("curriculumList")
    private List<Lesson> lessons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurriculumTypeName() {
        return curriculumTypeName;
    }

    public void setCurriculumTypeName(String curriculumTypeName) {
        this.curriculumTypeName = curriculumTypeName;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
