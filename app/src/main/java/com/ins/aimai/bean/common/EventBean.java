package com.ins.aimai.bean.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class EventBean implements Serializable {

    //注销
    public static final int EVENT_LOGOUT = 0xffa000;
    //登录
    public static final int EVENT_LOGIN = 0xffa001;
    //更新用户信息
    public static final int EVENT_USER_UPDATE = 0xffa002;
    //注册填写电话
    public static final int EVENT_REGIST_PHONE = 0xffa003;
    //选择行业
    public static final int EVENT_SELECT_TRADE = 0xffa004;
    //选择地址
    public static final int EVENT_SELECT_ADDRESS = 0xffa005;
    //课程详情加载介绍
    public static final int EVENT_LESSONDETAIL_INTRO = 0xffa005;
    //课程详情加载目录
    public static final int EVENT_LESSONDETAIL_DIRECTORY = 0xffa007;
    //课程详情选择目录
    public static final int EVENT_VIDEO_SELECT_DIRECTORY = 0xffa008;
    //已支付（不论成功失败或者取消）
    public static final int EVENT_PAYRESULT = 0xffa009;
    //首页切换到我的课程
    public static final int EVENT_HOME_TAB_LESSON = 0xffa010;
    //视频播放完成
    public static final int EVENT_VIDEO_FINISH = 0xffa011;
    //人脸采集页面返回结果
    public static final int EVENT_CAMERA_RESULT = 0xffa012;
    //添加员工成功
    public static final int EVENT_EMPLOY_ADD = 0xffa013;
    //课程分配成功（给课程分配用户）
    public static final int EVENT_USER_ALLOCAT = 0xffa014;
    //题库（练习题，出题库）下一页
    public static final int EVENT_QUESTIONBANK_NEXT = 0xffa015;
    //答题卡选择了一道试题
    public static final int EVENT_EXAMBOARD_SELECT = 0xffa016;
    //考试已交卷
    public static final int EVENT_EXAM_SUBMITED = 0xffa017;
    //课程分配成功(给用户分配课程)
    public static final int EVENT_LESSON_ALLOCAT = 0xffa018;

    private int event;
    private Map<String, Object> map = new HashMap<>();

    public EventBean() {
    }

    public EventBean(int event) {
        this.event = event;
    }

    public EventBean put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Object get(String key){
        return map.get(key);
    }

    //////////get & set///////////////

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
