package com.ins.aimai.bean.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/21.
 */

public class EventBean implements Serializable {

    public static final int EVENT_LOGIN = 0xffa004;
    public static final int EVENT_USER_UPDATE = 0xffa005;
    public static final int EVENT_REGIST_PHONE = 0xffa001;
    public static final int EVENT_SELECT_TRADE = 0xffa002;
    public static final int EVENT_SELECT_ADDRESS = 0xffa003;

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
