package com.ins.aimai.common;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class AppEvent {
    public static final Integer EVENT_UPDATE_ME = 0xfe01;

    private static final String FLAGMODE = "LOVE&INS";
    public static final String EVENT_PHONE_VALI = "EVENT_PHONE_VALI";

    public static String makeFlagStr(String flag, String str) {
        return flag + FLAGMODE + str;
    }

    public static String getFlag(String strSpc) {
        int i = strSpc.indexOf(FLAGMODE);
        return strSpc.substring(0, i);
    }

    public static String getStr(String strSpc) {
        int i = strSpc.indexOf(FLAGMODE);
        return strSpc.substring(i + FLAGMODE.length());
    }
}
