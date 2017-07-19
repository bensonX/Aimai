package com.ins.aimai.ui.dialog;

import android.content.Context;
import android.view.View;

import com.ins.aimai.R;
import com.ins.common.utils.DensityUtil;
import com.ins.common.view.singlepopview.BaseSinglePopupWindow;

/**
 * Created by Administrator on 2017/7/19.
 */

public class PopTextSize extends BaseSinglePopupWindow {

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
    }

}
