package com.ins.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间处理工具
 */
public class TimeUtil {

    private static GregorianCalendar gc = new GregorianCalendar();

    /**
     * 日期转字符串  格式:yyyy年MM月dd日 E (带星期)
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat("yyyy年MM月dd日 E").format(date);
        }
    }

    /**
     * 根据格式日期转字符串
     *
     * @param date
     * @return
     */
    public static String getTimeFor(String format, Date date) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(format).format(date);
        }
    }

    /**
     * 根据格式转日期
     *
     * @param format
     * @param dateStr
     * @return
     */
    public static Date getDateByStr(String format, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    /**
     * 把时间字符串转为指定格式的时间字符串
     *
     * @param formatfrom
     * @param formatto
     * @param dateStr
     * @return
     */
    public static String getStrByStr(String formatfrom, String formatto, String dateStr) {
        Date date = getDateByStr(formatfrom, dateStr);
        String ret = getTimeFor(formatto, date);
        return ret;
    }

    /**
     * 日期加减
     * *gc.add(1,-1)表示年份减一.  YEAR
     * gc.add(2,-1)表示月份减一.  MONTH
     * gc.add(3.-1)表示周减一.   WEEK_OF_YEAR
     * gc.add(5,-1)表示天减一.   DATE
     */
    public static Date add(Date date, int field, int value) {
        gc.setTime(date);
        gc.add(field, value);
        return gc.getTime();
    }

    public static String add(String format, String datestr, int field, int value) {
        Date date = TimeUtil.getDateByStr(format, datestr);
        Date datelast = TimeUtil.add(date, field, value);
        return TimeUtil.getTimeFor(format, datelast);
    }

    /**
     * 两天相减获取天数
     */
    public static int subDay(Date beginDate, Date endDate) {
        return (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
    }

    /**
     * 日起相减获取月份差
     */
    public static int subMouth(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == Calendar.SUNDAY) && (temp.get(Calendar.DATE) == Calendar.SUNDAY)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != Calendar.SUNDAY) && (temp.get(Calendar.DATE) == Calendar.SUNDAY)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == Calendar.SUNDAY) && (temp.get(Calendar.DATE) != Calendar.SUNDAY)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    public static String formatSecond(long s) {
        return formatSecond((int) (s / 1000));
    }

    public static String formatSecond(int s) {
        String html = "0秒";
        String format;
        Object[] array;
        int day = (s / (60 * 60 * 24));
        int hours = (s / (60 * 60));
        int minutes = (s / 60 - hours * 60);
        int seconds = (s - minutes * 60 - hours * 60 * 60);
        if (day > 0) {
            format = "%1$,d天%2$,d时%3$,d分%4$,d秒";
            array = new Object[]{day, hours, minutes, seconds};
        } else if (hours > 0) {
            format = "%1$,d时%2$,d分%3$,d秒";
            array = new Object[]{hours, minutes, seconds};
        } else if (minutes > 0) {
            format = "%1$,d分%2$,d秒";
            array = new Object[]{minutes, seconds};
        } else {
            format = "%1$,d秒";
            array = new Object[]{seconds};
        }
        html = String.format(format, array);
        return html;
    }

    public static String formatSecond(long time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        // 设置格式化器的时区为格林威治时区，否则格式化的结果不对，中国的时间比格林威治时间早8小时，比如0点会被格式化为8:00
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        return formatter.format(time);
    }

    public static int getHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
//        // 当前年
//        int year = cal.get(Calendar.YEAR);
//        // 当前月
//        int month = (cal.get(Calendar.MONTH)) + 1;
//        // 当前月的第几天：即当前日
//        int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
        // 当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        // 当前分
//        int minute = cal.get(Calendar.MINUTE);
//        // 当前秒
//        int second = cal.get(Calendar.SECOND);
//        // 0-上午；1-下午
//        int ampm = cal.get(Calendar.AM_PM);
//        // 当前年的第几周
//        int week_of_year = cal.get(Calendar.WEEK_OF_YEAR);
//        // 当前月的第几周
//        int week_of_month = cal.get(Calendar.WEEK_OF_MONTH);
//        // 当前年的第几天
//        int day_of_year = cal.get(Calendar.DAY_OF_YEAR);

        return hour;
    }

    public static void main(String[] args) {
//		System.out.println(TimeUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
//		System.out.println(TimeUtil.getNowTime("yyyy年MM月dd日 HH:mm"));
        System.out.println(TimeUtil.getTime(new Date()));
    }
}
