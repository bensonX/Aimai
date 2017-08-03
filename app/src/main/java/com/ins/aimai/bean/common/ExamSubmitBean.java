package com.ins.aimai.bean.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */

public class ExamSubmitBean implements Serializable{

    private int examinationId;
    private List<Integer> answers;

    public int getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(int examinationId) {
        this.examinationId = examinationId;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }
}
