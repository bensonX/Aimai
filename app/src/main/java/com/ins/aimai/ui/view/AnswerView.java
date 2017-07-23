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
    private TextView text_answerview_point;
    private TextView text_answerview_analysis;

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
        text_answerview_point = (TextView) findViewById(R.id.text_answerview_point);
        text_answerview_analysis = (TextView) findViewById(R.id.text_answerview_analysis);
    }

    private void initCtrl() {
        questionView.setNeedCheck(false);
    }

    private void initData() {
        if (isInEditMode()) {
            SpannableString spanStrAnswer = SpannableStringUtil.makeConorBkStr(context, R.color.am_blue, R.color.white, "答案", "A");
            SpannableString spanStrPoint = SpannableStringUtil.makeConorBkStr(context, R.color.com_dark, R.color.white, "考点", "常识判断、法律、其他法");
            SpannableString spanStrAnalysis = SpannableStringUtil.makeConorBkStr(context, R.color.com_dark, R.color.white, "解析", "耶和华说，我不再怜恤这地的居民。必将这民交给各人的邻舍，和他们王的手中。他们必毁灭这地，我也不救这民脱离他们的手。于是，我牧养这将宰的群羊，就是群中最困苦的羊。我拿着两根杖。一根我称为荣美，一根我称为联索。这样，我牧养了群羊。");
            text_answerview_answer.setText(spanStrAnswer);
            text_answerview_point.setText(spanStrPoint);
            text_answerview_analysis.setText(spanStrAnalysis);
        }
    }

    //################### 对外方法 #####################

    public QuestionView getQuestionView() {
        return questionView;
    }

    //################## get & set ####################

    //################## QuestionView 代理方法 ####################

    public void setData(QuestionBean questionBean) {
        questionView.setData(questionBean);
        SpannableString spanStrAnswer = SpannableStringUtil.makeConorBkStr(context, R.color.am_blue, R.color.white, "答案", questionBean.getAnswer());
        SpannableString spanStrPoint = SpannableStringUtil.makeConorBkStr(context, R.color.com_dark, R.color.white, "考点", questionBean.getPoint());
        SpannableString spanStrAnalysis = SpannableStringUtil.makeConorBkStr(context, R.color.com_dark, R.color.white, "解析", questionBean.getAnalysis());
        text_answerview_answer.setText(spanStrAnswer);
        text_answerview_point.setText(spanStrPoint);
        text_answerview_analysis.setText(spanStrAnalysis);
    }

    public void notifyDataSetChanged() {
        questionView.notifyDataSetChanged();
    }
}
