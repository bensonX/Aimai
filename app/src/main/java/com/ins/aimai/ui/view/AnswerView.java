package com.ins.aimai.ui.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.ui.adapter.ListAdapterQuestionItem;
import com.ins.common.entity.BaseSelectBean;
import com.ins.common.helper.SelectHelper;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.SpannableStringUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.ListViewLinearLayout;
import com.ins.common.view.SideBar;

import java.util.ArrayList;
import java.util.List;

/**
 * liaoinstan
 * AnswerView是QuestionView的装饰者，代理了QuestionView的对外接口，如果需要控制被装饰者QuestionView，getQuestionView()方法获取实例
 * AnswerView在QuestionView的基础上实现了答案、考点、解析
 */

public class AnswerView extends FrameLayout {

    private Context context;

    private QuestionView questionView;
    private TextView text_answerview_answer;

    public AnswerView(Context context) {
        this(context, null);
    }

    public AnswerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnswerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(context).inflate(R.layout.view_answer, this, true);
        initView();
        initCtrl();
        initData();
    }

    private void initView() {
        questionView = (QuestionView) findViewById(R.id.questionView);
        text_answerview_answer = (TextView) findViewById(R.id.text_answerview_answer);
    }

    private void initCtrl() {
    }

    private void initData() {
        if (isInEditMode()) {
        }

        int color_blue = ContextCompat.getColor(context, R.color.am_blue);
        int color_white = ContextCompat.getColor(context, R.color.white);
        SpannableString spannableString = SpannableStringUtil.makeConorBkStr(color_blue, color_white, "答案", "B");
        text_answerview_answer.setText(spannableString);
    }

    //################### 对外方法 #####################

    public QuestionView getQuestionView() {
        return questionView;
    }

    //################## get & set ####################

    //################## QuestionView 代理方法 ####################

    public void setData(QuestionBean questionBean) {
        questionView.setData(questionBean);
    }

    public void notifyDataSetChanged() {
        questionView.notifyDataSetChanged();
    }
}
