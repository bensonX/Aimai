package com.ins.aimai.common;

import com.ins.common.utils.NumUtil;

/**
 * Created by Administrator on 2017/7/24.
 */

public class AppHelper {

    public static String formatPrice(double ksum) {
        return NumUtil.num2half(ksum, 1);
    }
}
