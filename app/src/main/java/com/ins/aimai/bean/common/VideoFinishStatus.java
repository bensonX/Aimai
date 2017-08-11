package com.ins.aimai.bean.common;

import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/11.
 */

public class VideoFinishStatus implements Serializable {

    //是否提示去做练习题
    @SerializedName("isShowExercises")
    private int isShowExercises;
    //是否提示去做模拟题
    @SerializedName("isSimulationExercise")
    private int isSimulationExercise;
    //是否提示去做考试题
    @SerializedName("isExaminationQuestion")
    private int isExaminationQuestion;

    ///////////////////// 业务方法 //////////////////////

    public boolean isShowPractice() {
        return isShowExercises == 1;
    }

    public boolean isShowModel() {
        return isSimulationExercise == 1;
    }

    public boolean isShowOffi() {
        return isExaminationQuestion == 1;
    }

    public boolean isAllHide() {
        return !isShowPractice() && !isShowModel() && !isShowOffi();
    }

    /////////////////////////////////////////////////////


    public int getIsShowExercises() {
        return isShowExercises;
    }

    public void setIsShowExercises(int isShowExercises) {
        this.isShowExercises = isShowExercises;
    }

    public int getIsSimulationExercise() {
        return isSimulationExercise;
    }

    public void setIsSimulationExercise(int isSimulationExercise) {
        this.isSimulationExercise = isSimulationExercise;
    }

    public int getIsExaminationQuestion() {
        return isExaminationQuestion;
    }

    public void setIsExaminationQuestion(int isExaminationQuestion) {
        this.isExaminationQuestion = isExaminationQuestion;
    }
}
