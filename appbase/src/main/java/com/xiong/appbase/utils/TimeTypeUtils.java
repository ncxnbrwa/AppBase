package com.xiong.appbase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by iiMedia on 2017/12/12.
 * 时间工具类
 */

public class TimeTypeUtils {
    public static final String TAG = "TimeUtils";

    //获取24h前的时间戳
    public static long get24HourBeforeTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DLog.w(TAG, "24H:" + dateFormat.format(calendar.getTime()));
        return calendar.getTimeInMillis();
    }

    //获取一周前的时间戳
    public static long getWeekBeforeTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DLog.w(TAG, "一周:" + dateFormat.format(calendar.getTime()));
        return calendar.getTimeInMillis();
    }

    //获取30天前的时间戳
    public static long getMonthBeforeTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
//        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.DATE, -30);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DLog.w(TAG, "30天:" + dateFormat.format(calendar.getTime()));
        return calendar.getTimeInMillis();
    }

    //获取一天前0点的时间戳
    public static long getDayBeforeTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DLog.w(TAG, "一天:" + dateFormat.format(calendar.getTime()));
        return calendar.getTimeInMillis();
    }

    //获取昨天23点59分的时间戳
    public static long getDayBeforeEndTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, -1);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DLog.w(TAG, "一天:" + dateFormat.format(calendar.getTime()));
        return calendar.getTimeInMillis();
    }

    //时间戳转为yyMMddHH格式,needMulti为需不需要乘1000
    public static String timestamp2String8(String timestamp, boolean needMulti) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
        if (needMulti) {
            return sdf.format(new Date(Long.parseLong(timestamp) * 1000));
        } else {
            return sdf.format(new Date(Long.parseLong(timestamp)));
        }
    }

    //重载上面方法
    public static String timestamp2String8(long timestamp, boolean needMulti) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH");
        if (needMulti) {
            return sdf.format(new Date(timestamp * 1000));
        } else {
            return sdf.format(new Date(timestamp));
        }
    }

    //时间戳转为yyMMdd格式,needMulti为需不需要乘1000
    public static String timestamp2String6(String timestamp, boolean needMulti) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        if (needMulti) {
            return sdf.format(new Date(Long.parseLong(timestamp) * 1000));
        } else {
            return sdf.format(new Date(Long.parseLong(timestamp)));
        }
    }

    //重载
    public static String timestamp2String6(long timestamp, boolean needMulti) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        if (needMulti) {
            return sdf.format(new Date(timestamp * 1000));
        } else {
            return sdf.format(new Date(timestamp));
        }
    }

    //生成"2017-12-12"格式日期
    //生成"12-12"格式日期
    public static String getSpecDate(long timestamp) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(new Date(timestamp));
    }

    public static String timestamp2yyyyMMdd(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(new Date(timestamp));
    }

    //生成"09:00"格式时间
    public static String getTimeFormat(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(timestamp));
    }

    //通过指定范围生成需要日期,用在热点分析和情感趋势页面
    public static String timeRange2Date(int timeRange, int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -(timeRange - index));
        return getSpecDate(calendar.getTimeInMillis());
    }

    //通过指定范围生成需要日期,用在热点分析页面
    public static String timeRange2Hour24H(int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -(23 - index));
        calendar.set(Calendar.MINUTE, 0);
        return getTimeFormat(calendar.getTimeInMillis());
    }

    //通过指定范围生成需要日期,用在热点分析页面
    public static String timeRange2HourYesterday(int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(getDayBeforeEndTimestamp()));
        calendar.add(Calendar.HOUR_OF_DAY, -(23 - index));
        calendar.set(Calendar.MINUTE, 0);
        return getTimeFormat(calendar.getTimeInMillis());
    }
	
	//生成指定时间的时间戳
    public static long releaseTime2Stamp(String releaseTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(releaseTime);
        return date.getTime() / 1000;
    }
}
