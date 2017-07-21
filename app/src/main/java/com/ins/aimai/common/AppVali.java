package com.ins.aimai.common;

import android.text.TextUtils;

import com.ins.aimai.bean.User;
import com.ins.common.utils.ValidateUtil;

/**
 * Created by Administrator on 2017/5/12.
 */

public class AppVali {

    public static String login(String userName, String psw) {
        if (TextUtils.isEmpty(userName)) {
            return "请输入用户名";
        } else if (TextUtils.isEmpty(psw)) {
            return "请输入密码";
        } else if (!length(psw, 6, 32)) {
            return "密码长度必须为6-32位";
        } else {
            return null;
        }
    }

    public static String phone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "请输入手机号";
        } else if (!ValidateUtil.Mobile(phone)) {
            return "请输入正确的手机号";
        } else {
            return null;
        }
    }

    public static String regist_phone(String phone, String phone_old, String cold, String code_old) {
        if (TextUtils.isEmpty(phone)) {
            return "请输入手机号";
        } else if (!phone.equals(phone_old)) {
            return "你输入的号码没有验证过";
        } else if (!ValidateUtil.Mobile(phone_old)) {
            return "请输入正确的手机号";
        } else if (!cold.equals(code_old)) {
            return "验证码不正确";
        } else {
            return null;
        }
    }

    public static String regist_psw(String psw, String psw_repeat) {
        if (TextUtils.isEmpty(psw)) {
            return "请输入密码";
        } else if (!length(psw, 6, 32)) {
            return "密码长度必须为6-32位";
        } else if (!psw.equals(psw_repeat)) {
            return "两次输入号码不同";
        } else {
            return null;
        }
    }

    public static String updateUser(User user, String showName) {
        if (TextUtils.isEmpty(showName)) {
            return "请输入姓名";
        } else if (showName.trim().equals(user.getShowName().trim())) {
            return "没有任何修改";
        } else {
            return null;
        }
    }

    public static String regist_info(int type, String phone, String psw, String path, String u_name, String u_num, String c_name, String c_num, int c_tradeid, String g_name, String g_num, int cityid) {
        switch (type) {
            case 0:
                if (TextUtils.isEmpty(phone)) {
                    return "请输入手机号";
                } else if (TextUtils.isEmpty(psw)) {
                    return "请输入密码";
                } else if (TextUtils.isEmpty(path)) {
                    return "请上传头像";
                } else if (cityid == 0) {
                    return "请输入所在地";
                } else if (TextUtils.isEmpty(u_name)) {
                    return "请输入姓名";
                } else if (TextUtils.isEmpty(u_num)) {
                    return "请输入身份证";
                } else if (!ValidateUtil.IDcard(u_num)) {
                    return "身份证号格式不正确";
                } else {
                    return null;
                }
            case 1:
                if (TextUtils.isEmpty(phone)) {
                    return "请输入手机号";
                } else if (TextUtils.isEmpty(psw)) {
                    return "请输入密码";
                } else if (TextUtils.isEmpty(path)) {
                    return "请上传营业执照";
                } else if (cityid == 0) {
                    return "请输入所在地";
                } else if (TextUtils.isEmpty(c_name)) {
                    return "请输入企业名称";
                } else if (TextUtils.isEmpty(c_num)) {
                    return "请输入营业执照编码";
                } else if (c_tradeid == 0) {
                    return "请选择行业";
                } else {
                    return null;
                }
            case 2:
                if (TextUtils.isEmpty(phone)) {
                    return "请输入手机号";
                } else if (TextUtils.isEmpty(psw)) {
                    return "请输入密码";
                } else if (TextUtils.isEmpty(path)) {
                    return "请上传介绍信";
                } else if (cityid == 0) {
                    return "请输入所在地";
                } else if (TextUtils.isEmpty(g_name)) {
                    return "请输入机构名称";
                } else if (TextUtils.isEmpty(g_num)) {
                    return "请输入机构代码";
                } else {
                    return null;
                }
            default:
                return "验证错误";
        }
    }

    private static boolean length(String str, int min, int max) {
        return str.length() >= min && str.length() <= max;
    }
}
