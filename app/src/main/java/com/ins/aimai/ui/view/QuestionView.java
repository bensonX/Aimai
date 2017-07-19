package com.ins.aimai.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.TestBean;
import com.ins.aimai.ui.adapter.ListAdapterQuestionItem;
import com.ins.common.helper.LoadingViewHelper;
import com.ins.common.helper.SelectHelper;
import com.ins.common.utils.FontUtils;
import com.ins.common.utils.L;
import com.ins.common.view.ListViewLinearLayout;

/**
 * Created by liaoinstan on 2017/7/19.
 */

public class QuestionView extends FrameLayout implements AdapterView.OnItemClickListener{

    private Context context;
    private View showin;

    private TextView text_question_title;
    private ListViewLinearLayout list_qustion;
    private ListAdapterQuestionItem adapter;

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
    }

    private void initCtrl() {
        adapter = new ListAdapterQuestionItem(context);
        list_qustion.setAdapter(adapter);
        list_qustion.setOnItemClickListener(this);
        FontUtils.boldText(text_question_title);
    }

    private void initData() {
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.getResults().add(new TestBean());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TestBean bean = adapter.getResults().get(position);
        SelectHelper.selectAllSelectBeans(adapter.getResults(),false);
        bean.setSelect(true);
        adapter.notifyDataSetChanged();
    }
}
