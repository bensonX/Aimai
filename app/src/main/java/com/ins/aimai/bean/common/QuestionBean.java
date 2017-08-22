package com.ins.aimai.bean.common;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.ins.aimai.ui.view.QuestionView;
import com.ins.common.entity.BaseSelectBean;
import com.ins.common.utils.NumUtil;
import com.ins.common.utils.StrUtil;

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
    //选项实体
    private List<QuestionView.Option> optionBeans;
    //答案
    private String answer;
    //考点
    private String point;
    //解析
    private String analysis;
    //试题类型 0:单选题 1:多选题  2:判断题
    private int type;
    //试题类型
    private String typeName;


    public QuestionBean() {
    }

    public QuestionBean(String title, List<QuestionView.Option> optionBeans) {
        this.title = title;
        this.optionBeans = optionBeans;
    }

    ////////////////////  业务方法 /////////////////////

    //是否是多选题
    public boolean isMultSelectQuestion() {
        return type == 1;
    }

    //是否已经答题
    public boolean isChoosed() {
        if (TextUtils.isEmpty(getChooseStr())) {
            return false;
        } else {
            return true;
        }
    }

    //获取选择的答案 （A,B,C）
    public String getChooseStr() {
        if (StrUtil.isEmpty(getOptionBeans())) {
            return "";
        }
        String ret = "";
        for (QuestionView.Option option : getOptionBeans()) {
            if (option.isSelect()) {
                ret += NumUtil.intToABC(option.index) + ",";
            }
        }
        return StrUtil.subLastChart(ret, ",");
    }

    //获取正确的答案 （A,B,C）
    public String getAnswerStr() {
        if (StrUtil.isEmpty(getOptionBeans())) {
            return "";
        }
        String ret = "";
        for (QuestionView.Option option : getOptionBeans()) {
            if (option.isCorrect) {
                ret += NumUtil.intToABC(option.index) + ",";
            }
        }
        return StrUtil.subLastChart(ret, ",");
    }

    ////////////////////  get & set /////////////////////

    public List<QuestionView.Option> getOptionBeans() {
        return optionBeans;
    }

    public void setOptionBeans(List<QuestionView.Option> optionBeans) {
        this.optionBeans = optionBeans;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
