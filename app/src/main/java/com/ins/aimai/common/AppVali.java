package com.ins.aimai.common;

import android.text.TextUtils;

import com.ins.aimai.bean.Address;
import com.ins.aimai.bean.Trade;
import com.ins.aimai.bean.User;
import com.ins.common.utils.ValidateUtil;

/**
 * Created by Administrator on 2017/5/12.
 */

public class AppVali {

    public static String login(String userName, String psw) {
        if (TextUtils.isEmpty(userName)) {
            return "请输入手机号";
        } else if (!ValidateUtil.Mobile(userName)) {
            return "请输入正确的手机号";
        } else if (TextUtils.isEmpty(psw)) {
            return "请输入密码";
        } else if (!length(psw, 6, 32)) {
            return "密码格式不正确";
        } else {
            return null;
        }
    }

    public static String modifyPsw(String oldPsw, String newPsw, String newPsw_repeat) {
        if (isEmpty(oldPsw)) {
            return "请输入旧密码";
        } else if (isEmpty(newPsw)) {
            return "请输入新密码";
        } else if (isEmpty(newPsw_repeat)) {
            return "请再次输入新密码";
        } else if (!length(oldPsw, 6, 32)) {
            return "旧密码格式不正确";
        } else if (!length(newPsw, 6, 32)) {
            return "新密码格式不正确";
        } else if (!newPsw.equals(newPsw_repeat)) {
            return "两次输入不一致";
        } else {
            return null;
        }
    }

    public static String forgetPsw(String newPsw, String newPsw_repeat) {
        if (isEmpty(newPsw)) {
            return "请输入新密码";
        } else if (isEmpty(newPsw_repeat)) {
            return "请再次输入新密码";
        } else if (!length(newPsw, 6, 32)) {
            return "新密码格式不正确";
        } else if (!newPsw.equals(newPsw_repeat)) {
            return "两次输入不一致";
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

    public static String content(String content) {
        if (isEmpty(content)) {
            return "请输入内容";
        } else {
            return null;
        }
    }

    public static String faceRecord(User user, String faceId, String path, Trade trade, Address address) {
        if (user.isUser()) {
            if (address == null) {
                return "请先选择所在地";
            } else if (isEmpty(faceId) || isEmpty(path)) {
                return "请先进行人像采集";
            } else {
                return null;
            }
        } else if (user.isCompUser()) {
            if (address == null) {
                return "请先选择所在地";
            } else if (trade == null) {
                return "请先选择行业";
            } else if (isEmpty(path)) {
                return "请先录入营业执照";
            } else {
                return null;
            }
        } else if (user.isGovUser()) {
            if (address == null) {
                return "请先选择所在地";
            } else if (isEmpty(path)) {
                return "请先录入介绍信";
            } else {
                return null;
            }
        } else {
            return "不是移动端角色";
        }
    }

    public static String regist_phone(String phone, String phone_old, String cold, String code_old) {
        if (TextUtils.isEmpty(phone)) {
            return "请输入手机号";
        } else if (!phone.equals(phone_old)) {
            return "您的手机号与验证码不匹配";
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
            return "密码格式不正确";
        } else if (!psw.equals(psw_repeat)) {
            return "两次输入号码不同";
        } else {
            return null;
        }
    }

    public static String updateUser(User user, String showName, String path, Address address) {
        if (isEmpty(showName)) {
            return "请输入姓名";
        } else if (showName.trim().equals(user.getShowName().trim()) && TextUtils.isEmpty(path) && address == null) {
            //如果 没有修改名字，且没有选择照片，且没有选择所在地，则没有任何修改
            return "没有任何修改";
        } else {
            return null;
        }
    }

    public static String addOrderCount(int lessonId, int count) {
        if (lessonId == 0) {
            return "错误：无效的课程";
        } else if (count <= 0) {
            return "请输入购买课程数量";
        } else {
            return null;
        }
    }

    public static String addEmployee(User employ, String departmentName, String jobTitle) {
        if (employ == null || employ.getId() == 0) {
            return "错误：无效的用户";
        } else if (isEmpty(departmentName)) {
            return "请输入部门";
        } else if (isEmpty(jobTitle)) {
            return "请输入职位";
        } else {
            return null;
        }
    }

    public static String allocatUser(String userIds) {
        if (isEmpty(userIds)) {
            return "请至少选择一员工进行分配";
        } else {
            return null;
        }
    }

    public static String allocatLesson(String lessonIds) {
        if (isEmpty(lessonIds)) {
            return "请至少选择一个课程进行分配";
        } else {
            return null;
        }
    }

    public static String regist_info(int type, String phone, String psw, String path, String faceId, String u_name, String u_num, String c_name, String c_num, int c_tradeid, String g_name, String g_num, int cityid) {
        switch (type) {
            case 0:
                if (TextUtils.isEmpty(phone)) {
                    return "请输入手机号";
                } else if (TextUtils.isEmpty(psw)) {
                    return "请输入密码";
                } else if (TextUtils.isEmpty(faceId)) {
                    return "请先进行人脸采集";
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

    private static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }
}
