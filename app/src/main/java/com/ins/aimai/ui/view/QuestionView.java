package com.ins.aimai.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.QuestionBean;
import com.ins.aimai.bean.common.TestBean;
import com.ins.aimai.ui.adapter.ListAdapterQuestionItem;
import com.ins.common.entity.BaseSelectBean;
import com.ins.common.helper.SelectHelper;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.StrUtil;
import com.ins.common.view.ListViewLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaoinstan on 2017/7/19.
 */

public class QuestionView extends FrameLayout implements AdapterView.OnItemClickListener {

    private Context context;
    private View showin;

    private TextView text_question_title;
    private ListViewLinearLayout list_qustion;
    private ListAdapterQuestionItem adapter;

    protected QuestionBean questionBean;
    //是否可以勾选
    private boolean needCheck = true;

    public QuestionView(Context context) {
        this(context, null);
    }

    public QuestionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(context).inflate(R.layout.view_question, this, true);
        initView();
        initCtrl();
        initData();
    }

    private void initView() {
        text_question_title = (TextView) findViewById(R.id.text_question_title);
        list_qustion = (ListViewLinearLayout) findViewById(R.id.list_qustion);
        text_question_title.setText("");
    }

    private void initCtrl() {
        adapter = new ListAdapterQuestionItem(context);
        list_qustion.setAdapter(adapter);
        list_qustion.setOnItemClickListener(this);
        FontUtils.boldText(text_question_title);
    }

    private void initData() {
        if (isInEditMode()) {
            String title = "党的十八大以来，一些标志性话语深刻反映了中央治国理政新理念，其中，下列标志性话语与治国理政新理念对应错误是";
            final String option1 = "“刮骨疗毒，壮士割腕”——加强党风";
            final String option2 = "“踏石留印，抓铁有痕”--推动国防军队改革";
            final String option3 = "“凝聚共识，合作共赢”--发展大国外交";
            final String option4 = "“一个都不能少”——全面建设小康社会";
            List<String> options = new ArrayList<String>() {{
                add(option1);
                add(option2);
                add(option3);
                add(option4);
            }};
            QuestionBean questionBean = new QuestionBean(title, options);
            setData(questionBean);
        }
    }

    public void setData(QuestionBean questionBean) {
        this.questionBean = questionBean;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (questionBean != null) {
            text_question_title.setText(StrUtil.getSpace() + questionBean.getTitle() + "（ ）");
            adapter.getResults().clear();
            adapter.getResults().addAll(questionBean.getOptionBeans());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (needCheck) {
            switch (questionBean.getType()) {
                case 0://单选
                case 2://判断
                {
                    Option bean = adapter.getResults().get(position);
                    SelectHelper.selectAllSelectBeans(adapter.getResults(), false);
                    bean.setSelect(true);
                    adapter.notifyDataSetChanged();
                    break;
                }
                //多选
                case 1: {
                    Option bean = adapter.getResults().get(position);
                    bean.setSelect(!bean.isSelect());
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    public static class Option extends BaseSelectBean {

        public int id;

        public int index;

        public boolean isCorrect;

        public String content;

        public Option(String content) {
            this.content = content;
        }
    }

    //##################   get & set  ##############

    public boolean isNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
        adapter.setNeedCheck(needCheck);
        adapter.notifyDataSetChanged();
    }
}
