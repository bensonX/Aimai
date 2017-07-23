package com.ins.aimai.bean.common;

import com.ins.aimai.ui.view.QuestionView;
import com.ins.common.entity.BaseSelectBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 * 选择题实体类
 */

public class QuestionBean extends BaseSelectBean implements Serializable {
    private int id;
    //题号
    private int num;
    //题目
    private String title;
    //选项
    private List<String> options;
    //选项实体
    private List<QuestionView.Option> optionBeans;
    //答案
    private String answer;
    //考点
    private String point;
    //解析
    private String analysis;


    public QuestionBean() {
    }

    public QuestionBean(String title, List<String> options) {
        this.title = title;
        this.options = options;
    }

    ////////////////////  转换方法 /////////////////////

    private List<QuestionView.Option> transStrs2Options(List<String> optionStrs) {
        List<QuestionView.Option> options = new ArrayList<>();
        if (optionStrs != null) {
            for (String optionStr : optionStrs) {
                options.add(new QuestionView.Option(optionStr));
            }
        }
        return options;
    }

    public List<QuestionView.Option> getOptionBeans() {
        if (optionBeans == null) {
            setOptionBeans(transStrs2Options(options));
        }
        return optionBeans;
    }

    public void setOptionBeans(List<QuestionView.Option> optionBeans) {
        this.optionBeans = optionBeans;
    }

    ////////////////////  get & set /////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }


}
