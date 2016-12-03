package com.sunzequn.geo.data.geonamesplus;

/**
 * @author sunzequn
 */
public class DateUtils {

    /**
     * 获得某年某月的天数
     *
     * @param year  年份
     * @param month 月份
     * @return 该月的天数
     */
    public static int daysOfMonth(int year, int month) {
        if (month == 2) {
            if (isLeap(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 11) {
            return 31;
        }
        return 30;
    }

    /**
     * 判断年份是否是闰年
     *
     * @param year 年份
     * @return 是闰年返回true，否则返回false
     */
    public static boolean isLeap(int year) {
        return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
    }

}
