/*
 * Copyright (C) 2013 www.418log.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vivian.apputil.util;

import android.text.TextUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 描述：日期处理类.
 *
 * @version v1.0
 */
public class AppDateUtil {

    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String chatDateFormatYMDHMS = "yyyy-MM-dd HH:mm";
    public static String dateFormatY_M_D_H_M_S = "yyyy-MM-dd-HH-mm-ss";
    public static String dateFormatYMDHM_upLoad = "MM-dd HH:mm:ss";
    public static String dateSaveImg = "MM.dd_HH.mm";
    public static String dateFormatMD1 = "MM月dd日";
    public static String dateFormatYMD = "yyyy年MM月dd日";
    public static String dateFormatHM = "HH:mm";
    public static String dateFormatCamera = "HH_mm_ss";

    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param milliseconds the milliseconds
     * @param format       格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds, String format) {
        String thisDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Date dt = new Date(milliseconds);
            thisDateTime = mSimpleDateFormat.format(dt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }


    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;

    }


    public static String getWeekOfDate(long time) {

        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            String d = mSimpleDateFormat.format(time);
            Date date = mSimpleDateFormat.parse(d);
            String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            return weekDays[w];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getWeekOfDateChat(long time) {

        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            String d = mSimpleDateFormat.format(time);
            Date date = mSimpleDateFormat.parse(d);
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            return weekDays[w];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }


    public static boolean courseIsEnd(String currentTime, String beginTime) {
        long currentTimelong = Long.parseLong(currentTime);
        long beginTimelong = Long.parseLong(beginTime);
        if (beginTimelong - currentTimelong <= 0)
            return true;
        else
            return false;
    }

    public static boolean courseIsEnd(long currentTime, long beginTime) {

        if (beginTime - currentTime <= 0)
            return true;
        else
            return false;
    }

    public static boolean timeCompare(long currentTime, long beginTime, int compareTime) {

        if (beginTime - currentTime <= compareTime)
            return true;
        else
            return false;
    }

    public static boolean timeCompareEqual(long currentTime, long beginTime, int compareTime) {
        if (beginTime - currentTime == compareTime)
            return true;
        else
            return false;
    }

    public static boolean courseIsStartCountDown(long currentTime, long beginTime) {
        if (beginTime - currentTime >= 60 * 60)
            return false;
        else
            return true;
    }

    public static boolean courseIsStart(long currentTime, long beginTime) {
        if (beginTime - currentTime <= 0)
            return false;
        else
            return true;
    }

    public static boolean courseIsRealStart(long currentTime, long beginTime) {
        if (currentTime >= beginTime)
            return true;
        else
            return false;
    }

    public static boolean courseIsReal5Start(long currentTime, long beginTime) {
        if (currentTime + 5 * 60 >= beginTime)
            return true;
        else
            return false;
    }

    /**
     * timeNow<beforeTimeString-3
     *
     * @param serverTime
     * @param systemTime
     * @return
     */
    public static boolean timeCompare3Minute(String serverTime, String systemTime) {
        long systemTimeLong = Long.parseLong(systemTime);//系统时间
        long serverTimeLong = Long.parseLong(serverTime) * 1000;//服务器时间
        return systemTimeLong - 3 * 60 * 1000 < serverTimeLong;
    }

    /**
     * 比较是否在5分钟以内
     *
     * @param serverTime
     * @param systemTime
     * @return
     */
    public static boolean timeCompare5Minute(String serverTime, String systemTime) {
        long systemTimeLong = Long.parseLong(systemTime);//系统时间
        long serverTimeLong = Long.parseLong(serverTime);//服务器时间
        return serverTimeLong - 5 * 60 < systemTimeLong;
    }

    public static boolean timeCompare20Minute(String serverTime, String systemTime) {
        long systemTimeLong = Long.parseLong(systemTime);//系统时间
        long serverTimeLong = Long.parseLong(serverTime);//服务器时间
        return serverTimeLong - 20 * 60 < systemTimeLong;
    }


    /**
     * 将int型的时间格式转换成xx:xx:xx格式的时间
     *
     * @param @param  time
     * @param @return
     * @return String
     * @description
     * @author jiaBF
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                    /*if (hour > 99)
                        return "99:59:59";*/
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String secToTime1(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                    /*if (hour > 99)
                        return "99:59:59";*/
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String showFormatTime(int millisecond) {
        //将毫秒转换为秒
        int second = millisecond / 1000;
        //计算小时
        int hh = second / 3600;
        //计算分钟
        int mm = second % 3600 / 60;
        //计算秒
        int ss = second % 60;
        //判断时间单位的位数
        String str = null;
        if (hh != 0) {//表示时间单位为三位
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        return str;
    }


    public static boolean isToday(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {//格式化为相同格式
            return true;
        } else {
            return false;
        }


    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            System.out.println("判断day2 - day1 : " + (day2 - day1));

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static String getSecondTimestamp(Date date) {
        if (null == date) {
            return "";
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return timestamp.substring(0, length - 3);
        } else {
            return "";
        }
    }

    public static String getTime(String time, String before) {

        if (isToday(new Date(Long.parseLong(time) * 1000))) {
            //T日的所有时间显示【xx：xx】
            return getStringByFormat(Long.parseLong(time), dateFormatHM);
        } else {
            //不是T日
            switch (differentDays(new Date(Long.parseLong(time) * 1000), new Date())) {
                case 1:
                    return "昨天" + getStringByFormat(Long.parseLong(time), dateFormatHM);
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    return getWeekOfDateChat(Long.parseLong(time) * 1000) + getStringByFormat(Long.parseLong(time), dateFormatHM);
                default:
                    return getStringByFormat(Long.parseLong(time), chatDateFormatYMDHMS);

            }

        }
    }


    public static String getDay(String time) {
        String showDay = null;
        String nowTime = returnTime();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date now = df.parse(nowTime);
            java.util.Date date = df.parse(time);
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            if (day >= 365) {
                showDay = time.substring(0, 10);
            } else if (day >= 1 && day < 365) {
                showDay = time.substring(5, 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showDay;
    }

    public static String returnTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1488804520419";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss.SSS";
     * @return 返回结果 如："2017-03-06 20:48:40.020";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        Long timestamp = Long.parseLong(timestampString + "000");
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    public static long getFormatTime(String dateString, String formats) {
        // 格式化时间，转换成long类型
        Date date = null;
        SimpleDateFormat formatTime2long = new SimpleDateFormat(formats);
        try {
            date = formatTime2long.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long longFormatTime = date.getTime();
        // 有时候，自己电脑或者手机上的时间，会和服务器差8个小时，这个时候，就用下面注释的这句。看情况而定
        return longFormatTime;
    }


    public static String TimeStamp2Date1(Long timestampString, String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestampString));
        return date;
    }


    //判断选择的日期是否是今天
    public static boolean isToday(String time) {
        Long timestamp = Long.parseLong(time + "000");
        return isThisTime(timestamp, "yyyy-MM-dd");
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    //获取文件时间
    public static String fileTime(String path) {
        File f = new File(path);
        Date date = new Date(f.lastModified()); //这个是最后修改时间
        return getSecondTimestamp(date);
    }

    public static String currentTime() {
        return getSecondTimestamp(new Date(System.currentTimeMillis()));
    }


    /**
     * 时间戳格式转换
     */
    public static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static final String NO_COMPARE_RESULT = "no_compare_result";

    public static String getNewChatTime(long timestamp, long compareTime) {
        if (compareTime != 0) {
            long compareDate = timestamp - compareTime;//精确到毫秒级
            if (compareDate <= 5 * 60 * 60 * 1000) {
                //5分钟以内
                return NO_COMPARE_RESULT;
            }
        }
        String result;
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timestamp);

        String timeFormat = "M月d日 HH:mm";
        String yearTimeFormat = "yyyy年M月d日 HH:mm";
        String am_pm = "";
        int hour = otherCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 6) {
            am_pm = "凌晨";
        } else if (hour >= 6 && hour < 12) {
            am_pm = "早上";
        } else if (hour == 12) {
            am_pm = "中午";
        } else if (hour > 12 && hour < 18) {
            am_pm = "下午";
        } else if (hour >= 18) {
            am_pm = "晚上";
        }
        timeFormat = "M月d日 " + am_pm + "HH:mm";
        yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timestamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timestamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if (dayOfWeek != 1) {//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1] + getHourAndMin(timestamp);
                            } else {
                                result = getTime(timestamp, timeFormat);
                            }
                        } else {
                            result = getTime(timestamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timestamp, timeFormat);
                        break;
                }
            } else {
                result = getTime(timestamp, timeFormat);
            }
        } else {
            result = getYearTime(timestamp, yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     *
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }
}
