package com.ins.common.utils.others;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;

/**
 * Created by Administrator on 2017/8/10.
 */

public class CommonUtil {
    //设置CollapsingToolbarLayout是否可以响应滚动事件
    public static void setEnableCollapsing(CollapsingToolbarLayout collapsingToolbarLayout, boolean enableCollapsing) {
        int enable = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP;
        int disable = 0;
        AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        if (enableCollapsing) {
            mParams.setScrollFlags(enable);
        } else {
            mParams.setScrollFlags(disable);
        }
    }
}
