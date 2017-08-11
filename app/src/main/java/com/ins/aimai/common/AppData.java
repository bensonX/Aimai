package com.ins.aimai.common;

import com.ins.aimai.bean.User;
import com.ins.aimai.bean.common.CheckPoint;
import com.ins.common.utils.SharedPrefUtilV2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class AppData {


    public static class App {

        private static final String SHARENAME = "app_config";
        private static final String KEY_TOKEN = "token";
        private static final String KEY_USER = "user";
        private static final String KEY_TEXTSIZE_EXAM = "video_time";

        public static void saveToken(String token) {
            SharedPrefUtilV2.open(SHARENAME).putString(KEY_TOKEN, token);
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

        public static void saveTextSizeExam(int textsize) {
            SharedPrefUtilV2.open(SHARENAME).putInt(KEY_TEXTSIZE_EXAM, textsize);
        }

        public static int getTextSizeExam() {
            return SharedPrefUtilV2.open(SHARENAME).getInt(KEY_TEXTSIZE_EXAM);
        }

        public static List<CheckPoint> getVideoCheckPoint() {
            return new ArrayList<CheckPoint>() {{
                add(new CheckPoint(0, 1f / 3f));
                add(new CheckPoint(1, 2f / 3f));
            }};
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
         * 资源服务器地址
         */
        public static String domainRes;
        public static String domainEye = "http://api.eyekey.com/";

        /**
         * 接口请求地址
         */
        public static String version = "api/updateAPK/version.json";                                   //客户端检查更新
        public static String upload = "images/res/upload";                                                 //上传文件

        public static String bannerInfo = "api/page/app/bannerInfo";                                            //banner详情?bannerId=1
        public static String newsInfo = "api/page/app/newsInfo";                                                //资讯详情?newsId=2

        public static String quelity = "http://aq415.com/zixun-show.aspx?zixun_id=42";                //资质
        public static String about = "http://aq415.com/zixun-show.aspx?zixun_id=33";                  //关于我们
        public static String netpoint = "http://aq415.com/zixun-show.aspx?zixun_id=35";               //网点

        //eyekey人脸识别接口
        public static String eyeCheck = domainEye + "face/Check/checking";                                                //人像解析
        public static String eyeCompare = domainEye + "face/Match/match_compare";                                        //人像对比

        public static String getVideoUrl(String url) {
            return domainRes + "video/" + url;
        }
    }
}
