package com.ins.aimai.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ins.aimai.R;
import com.ins.aimai.bean.common.EventBean;
import com.ins.aimai.ui.view.QuestionView;
import com.ins.common.utils.DensityUtil;
import com.ins.common.view.singlepopview.BaseSinglePopupWindow;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/7/19.
 */

public class PopTextSize extends BaseSinglePopupWindow implements View.OnClickListener {

    private View img_popsize_big;
    private View img_popsize_nomal;
    private View img_popsize_small;

    public PopTextSize(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.pop_textsize;
    }

    @Override
    public void initBase() {
        setWidth(DensityUtil.dp2px(context, 45));
        img_popsize_big = getContentView().findViewById(R.id.img_popsize_big);
        img_popsize_nomal = getContentView().findViewById(R.id.img_popsize_nomal);
        img_popsize_small = getContentView().findViewById(R.id.img_popsize_small);
        img_popsize_big.setOnClickListener(this);
        img_popsize_nomal.setOnClickListener(this);
        img_popsize_small.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EventBean eventBean = new EventBean(EventBean.EVENT_EXAM_TEXISIZE);
        switch (v.getId()) {
            case R.id.img_popsize_big:
                eventBean.put("size", QuestionView.TEXTSIZE_BIG);
                break;
            case R.id.img_popsize_nomal:
                eventBean.put("size", QuestionView.TEXTSIZE_NOMAL);
                break;
            case R.id.img_popsize_small:
                eventBean.put("size", QuestionView.TEXTSIZE_SMALL);
                break;
        }
        EventBus.getDefault().post(eventBean);
        dismiss();
    }
}
