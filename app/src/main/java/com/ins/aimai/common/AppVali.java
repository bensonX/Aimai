package com.ins.aimai.common;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/5/12.
 */

public class AppVali {

    //提交结项验收数据
    public static String login(String userName, String psw) {
        if (TextUtils.isEmpty(userName)) {
            return "请输入用户名";
        } else if (TextUtils.isEmpty(psw)) {
            return "请输入密码";
        } else {
            return null;
        }
    }
}
