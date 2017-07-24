package com.ins.aimai.bean.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class EventBean implements Serializable {

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
