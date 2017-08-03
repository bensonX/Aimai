package com.ins.aimai.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
public class ExamResult implements Serializable {
    private int examinationId;
    private int isCorrect;
    private List<Integer> answers;

    //////////////  业务方法 //////////////////////

    public boolean isCorrect() {
        return isCorrect == 1;
    }

    ///////////////////////////////////////////////

    public int getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(int examinationId) {
        this.examinationId = examinationId;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }
}
