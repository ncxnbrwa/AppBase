package com.xiong.appbase.utils;

import com.xiong.appbase.http.UploadImgEngine;
import com.xiong.appbase.http.UploadImgService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiong on 2017/12/12.
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

    //获取当前△T对应的单位时间值
    public static String getMarginTime(long getTime) {
        String stander;
        long currentTime = System.currentTimeMillis();
        long marginTime = currentTime - getTime;            //得到一个毫秒值
        double t1 = marginTime / 1000 / 60;                     //得到分钟值
        double t2 = t1 / 60;                                  //得到小时值
        double t3 = t2 / 24;                                  //得到天值
        // new BigDecimal(t1).setScale(0, BigDecimal.ROUND_HALF_UP) 此方法用于四舍五入取整
        if (t3 >= 1) {
            //如果大于一天，一天为单位返回
            stander = getyyyyMMddHHmm(getTime);
        } else if (t2 >= 1) {
            //如果大于一小时，一小时为单位返回
            stander = new BigDecimal(t2).setScale(0, BigDecimal.ROUND_HALF_UP) + "小时前";
        } else {
            stander = new BigDecimal(t1).setScale(0, BigDecimal.ROUND_HALF_UP) + "分钟前";
            if (t1 < 1)
                stander = "刚刚";
        }
        return stander;
    }

    //获取当前△T对应的单位时间值
    public static String getMarginTimeWithinDay(long getTime) {
        String stander;
        long currentTime = System.currentTimeMillis();
        long marginTime = (currentTime - getTime);           //得到一个毫秒值
        marginTime = marginTime / 4;
        double t1 = marginTime / 1000 / 60;                     //得到分钟值
        double t2 = t1 / 60;                                  //得到小时值
        double t3 = t2 / 24;                                  //得到天值
        // new BigDecimal(t1).setScale(0, BigDecimal.ROUND_HALF_UP) 此方法用于四舍五入取整
        if (t3 >= 1) {
            //如果大于一天，一天为单位返回
//            stander = new BigDecimal(t3).setScale(0, BigDecimal.ROUND_HALF_UP) +"天前";
            stander = "";
        } else if (t2 >= 1 && t2 != 24) {
            //如果大于一小时，一小时为单位返回
            stander = new BigDecimal(t2).setScale(0, BigDecimal.ROUND_HALF_UP) + "小时前";
        } else {
            if (t1 < 1) {
                stander = "刚刚";
            } else {
                stander = new BigDecimal(t1).setScale(0, BigDecimal.ROUND_HALF_UP) + "分钟前";
            }
        }
        return stander;
    }

    //获取当前△T对应的单位时间值
    public static boolean getMarginTimeTOTWOHours(long getTime) {
        boolean stander = false;
        long currentTime = System.currentTimeMillis();
        long marginTime = (currentTime - getTime) / 4;            //得到一个毫秒值
        double t1 = marginTime / 1000 / 60;                     //得到分钟值
        double t2 = t1 / 60;                                  //得到小时值
        if (t2 >= 2) {
            stander = true;
        }
        return stander;
    }

    public static String getMarginTimeOrDate(long getTime) {
        String stander;
        long currentTime = System.currentTimeMillis();
        long marginTime = currentTime - getTime;            //得到一个毫秒值
        double t1 = marginTime / 1000 / 60;                     //得到分钟值
        double t2 = t1 / 60;                                  //得到小时值
        double t3 = t2 / 24;                                  //得到天值
        // new BigDecimal(t1).setScale(0, BigDecimal.ROUND_HALF_UP) 此方法用于四舍五入取整
        if (t3 >= 1) {
            //如果大于一天，返回日期
            stander = getyyyyMMdd(getTime);
        } else if (t2 >= 1) {
            //如果大于一小时，一小时为单位返回
            stander = new BigDecimal(t2).setScale(0, BigDecimal.ROUND_HALF_UP) + "小时前";
        } else {
            stander = new BigDecimal(t1).setScale(0, BigDecimal.ROUND_HALF_UP) + "分钟前";
            if (t1 < 1)
                stander = "刚刚";
        }
        return stander;
    }

    public static String ConvertToHHmmss(double sec) {
        long ms = (long) (sec * 1000);
        SimpleDateFormat formatter;
        if (sec >= 3600) {
            formatter = new SimpleDateFormat("HH:mm:ss");
        } else {
            formatter = new SimpleDateFormat("mm:ss");
        }
        String hms = formatter.format(ms);
        return hms;
    }

    public static String getHHMM(long getTime) {
        String hhmm = "";
        SimpleDateFormat format = new SimpleDateFormat("HH");
        int hour = Integer.valueOf(format.format(getTime));
        format = new SimpleDateFormat("mm");
        int minute = Integer.valueOf(format.format(getTime));
        if (hour < 10) {
            hhmm = "0" + hour + ":";
        } else {
            hhmm = hour + ":";
        }
        if (minute < 10) {
            hhmm = hhmm + "0" + minute;
        } else {
            hhmm = hhmm + minute;
        }
        return hhmm;
    }

    public static int getDataVale(long getTime) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        int dataValue = Integer.valueOf(format.format(getTime));
        return dataValue;
    }

    public static int getMouthVale(long getTime) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        int mouthValue = Integer.valueOf(format.format(getTime));
        return mouthValue;
    }

    public static String getHHmm(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String ret = formatter.format(ms);
        return ret;
    }

    public static String getMMDD(long getTime) {
        String hhmm = "";
        SimpleDateFormat format = new SimpleDateFormat("MM");
        int mouth = Integer.valueOf(format.format(getTime));
        format = new SimpleDateFormat("dd");
        int data = Integer.valueOf(format.format(getTime));
        hhmm = mouth + "月" + data + "日";
        return hhmm;
    }

    public static String getyyyyMMddHHmm(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String ret = formatter.format(ms);
        return ret;
    }

    public static String getyyyyMMdd(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String ret = formatter.format(ms);
        return ret;
    }

    //获取两个日期的时间间隔
    public static int countDateInterval(long startTime, long endTime) {
        //把日期的时分秒毫秒全部置为0,只比对日的单位
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(new Date(startTime));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date(endTime));
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);
        return (int) ((endCalendar.getTime().getTime() - startCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    //时间格式化视频播放时间,把毫秒值转换成00:00:00
    private String formatSeconds(long millisecond) {
        int second = (int) (millisecond / 1000);
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String str = "";
        if (hh != 0) {
            //02d代表至少两位的十进制整数,5会变成05
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        return str;
    }

}
