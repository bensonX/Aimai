package com.ins.aimai.bean;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ins.common.utils.StrUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */

public class ExamResultPojo implements Serializable {

    private int resultScore;
    private int correctNum;
    private int passScore;
    private List<ExamResult> jsonArray;

    /////////////////业务方法 ////////////////////

    public boolean isPass() {
        return resultScore >= passScore;
    }

    /////////////////////////////////////////////

    public int getResultScore() {
        return resultScore;
    }

    public List<ExamResult> getJsonArray() {
        return jsonArray;
    }

    public void setResultScore(int resultScore) {
        this.resultScore = resultScore;
    }

    public int getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(int correctNum) {
        this.correctNum = correctNum;
    }

    public int getPassScore() {
        return passScore;
    }

    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    public void setJsonArray(List<ExamResult> jsonArray) {
        this.jsonArray = jsonArray;
    }


}
