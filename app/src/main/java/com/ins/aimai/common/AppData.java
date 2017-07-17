package com.ins.aimai.common;

import com.ins.aimai.bean.User;
import com.ins.common.utils.SharedPrefUtilV2;

/**
 * Created by Administrator on 2017/7/17.
 */

public class AppData {


    public static class App {

        private static final String SHARENAME = "app_config";
        private static final String KEY_TOKEN = "token";
        private static final String KEY_USER = "user";

        public static void saveToken(String token) {
            SharedPrefUtilV2.open(SHARENAME).put(KEY_TOKEN, token);
        }

        public static String getToken() {
            return SharedPrefUtilV2.open(SHARENAME).getString(KEY_TOKEN);
        }

        public static void removeToken() {
            SharedPrefUtilV2.open(SHARENAME).remove(KEY_TOKEN);
        }

        public static void saveUser(User user) {
            SharedPrefUtilV2.open(SHARENAME).put(KEY_USER, user);
        }

        public static User getUser() {
            return (User) SharedPrefUtilV2.open(SHARENAME).get(KEY_USER);
        }

        public static void removeUser() {
            SharedPrefUtilV2.open(SHARENAME).remove(KEY_USER);
        }
    }

    /**
     * 记录了app中所有全局控制常量
     */
    public static class Config {
        public static boolean showVali = false;                 //显示验证码（仅测试）
        public static boolean showTestToast = false;            //打印测试信息到窗口（仅测试）
    }

    /**
     * 记录了app中所有的请求连接地址
     */
    public static class Url {

        /**
         * 接口请求地址
         */
        public static String version = "updateAPK/version_feast.json";                                    //客户端检查更新
    }
}
