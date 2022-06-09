package com.wcy.code.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Wcy
 * @Date 2022/5/27 18:57
 */
public class DateUtils {
    //创建函数获取当前时间
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return sdf.format(date);
    }


    public static String timeFormat(String Time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        double start = 0.000;
        try {
            start = (double) sdf.parse(Time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(new Date((long) (start)));
    }


    //获取两个时间字符串的时间差，返回秒 保留三位小数
    public static String getTimeDifference(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        double start = 0.000;
        double end = 0.000;
        try {
            start = (double) sdf.parse(startTime).getTime();
            end = (double) sdf.parse(endTime).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf((int) (end - start) / 1000);
    }

    //获取两个时间字符串的时间差，返回秒 保留三位小数
    public static String getTimeDifferenceHour(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        double start = 0.000;
        double end = 0.000;
        try {
            start = (double) sdf.parse(startTime).getTime();
            end = (double) sdf.parse(endTime).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf((int) (end - start) / 1000);
    }

    public static String getTime(String timeInSeconds) {
        //将秒的字符串转换为 时分秒
        int seconds = Integer.parseInt(timeInSeconds);
        int hour = seconds / 3600;
        int minute = (seconds - hour * 3600) / 60;
        int second = seconds - hour * 3600 - minute * 60;
        String hou = null, min = null, sec = null;
        if (hour < 10) hou = "0" + hour;
        else hou = hour + "";
        if (minute < 10) min = "0" + minute;
        else min = minute + "";
        if (second < 10) sec = "0" + second;
        else sec = second + "";
        return hou + ":" + min + ":" + sec;
    }

    //根据时间戳返回日期 例如：15:00:00
    public static String getTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(time));
    }

    public static String getAllTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time)).split(" ")[1];
    }

    //求两个时间的平均时间，返回yyyy-MM-dd HH:mm:ss
    public static String getAvgTime(String startTime, String endTime) {
        int start = getSeconds(startTime);
        int end = getSeconds(endTime);
        int avg = (start + end) / 2;
        return getTime(String.valueOf(avg));
    }

    //将时分秒转换为秒
    public static int getSeconds(String time) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        int second = Integer.parseInt(times[2]);
        return hour * 3600 + minute * 60 + second;
    }


}
