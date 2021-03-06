package com.meehoo.biz.common.util;

import cn.hutool.core.date.DateTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM");
    public static final long DayLong = 1000 * 60 * 60 * 24;

    public static List<Date> getEveryMonthOfYear() {
        Date now = getTodayBegin();
        int total = get(now, Calendar.MONTH);
        List<Date> result = new ArrayList<>(total);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        for (int n = 0; n < total; n++) {
            calendar.set(Calendar.MONTH, n);
            result.add(calendar.getTime());
        }
        return result;
    }

    public static List<Date> getEveryDayOfMonth() {
        Date now = getTodayBegin();
        int total = get(now, Calendar.DAY_OF_MONTH);
        List<Date> result = new ArrayList<>(total);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);


        for (int n = 1; n <= total; n++) {
            calendar.set(Calendar.DAY_OF_MONTH, n);
            result.add(calendar.getTime());
        }
        return result;
    }

    public static String timeToString(Date date) {
        if (date != null)
            return sdf1.format(date);
        return "";
    }

    public static String timeToNumber(Date date) {
        if (date != null)
            return sdf.format(date);
        return "";
    }

    public static String dateToString(Date date) {
        if (date != null)
            return sdf2.format(date);
        return "";
    }

    public static String ymToString(Date date) {
        if (date != null)
            return sdf3.format(date);
        return "";
    }

    public static String longToString(long currentTime) {
        String time = sdf.format(Long.valueOf(currentTime));
        return time;
    }

    public static Date toDate(Object object) throws Exception {
        return null;
    }

    public static Date stringToDate(String string) {
        if (StringUtil.stringIsNull(string)) {
            return null;
        }
        try {
            if (string.length() == 19) {
                return sdf1.parse(string);
            } else if (string.length() == 10) {
                return sdf2.parse(string);
            } else if (string.length() == 14) {
                return sdf.parse(string);
            } else if (string.length() == 7) {
                return sdf3.parse(string);
            } else {
                throw new RuntimeException("??????????????????");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getTodayBegin() {
        return getDateDayBegin(new Date());
    }

    public static Date getDateDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getDateDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date getThisMonthBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getThisYearBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static int countYear(Date date) {
        Date now = new Date();
        return (int) ((now.getTime() - date.getTime()) / (long) (1000 * 60 * 60 * 24 * 365));
    }

    public static int countDay(Date date) {
        Date now = new Date();
        return (int) ((now.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static Date before(int yearQty) {
        Calendar calendar = Calendar.getInstance();
        int n = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, n - yearQty);
        return calendar.getTime();
    }

    public static Date before(int qty, int type) {
        Calendar calendar = Calendar.getInstance();
        int n = calendar.get(type);
        calendar.set(type, n - qty);
        return calendar.getTime();
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int get(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int result = calendar.get(type);
        if (type == Calendar.MONTH) {
            result++;
        }
        return result;
    }

    // ??????????????????
    public static int getDayQtyOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getMovedDate(int qty) {
        long timeLong = new Date().getTime();
        return new Date(timeLong + qty * DayLong);
    }

    //?????????N?????????
    public static List<String> getSomeDate(int n) {
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            DateTime dateTime = cn.hutool.core.date.DateUtil.offsetDay(new Date(), i);
            dateList.add(cn.hutool.core.date.DateUtil.formatDate(dateTime));
        }
        return dateList;
    }

    //?????????N??????
    public static List<String> getSomeMonth(int n) {
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            DateTime dateTime = cn.hutool.core.date.DateUtil.offsetMonth(new Date(), i);
            dateList.add(cn.hutool.core.date.DateUtil.format(dateTime, sdf3));
        }
        return dateList;
    }

    //???????????????????????????????????????
    public static List<String> getAllDate(String startTime, String endTime) {
        // ?????????????????????
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// ?????????1(????????????)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static List<String> getBetweenMonths(String startTime, String endTime) {
        List<String> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.MONTH, +1);// ?????????1(????????????)
            while (tempStart.before(tempEnd)) {
                result.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    // ??????????????????
    public static String timeToDayString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (date != null)
            return sdf.format(date);
        return "";
    }

    //??????????????????????????????
    public static Date getFirstDayOfMonth(Date firstDate) {
        String date = ymToString(firstDate);
        int year = Integer.parseInt(date.substring(0, 4));  //????????????????????????????????????int
        int month = Integer.parseInt(date.substring(5, 7));    //?????????????????????????????????int

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);    //????????????
        cal.set(Calendar.MONTH, month - 1);  //????????????
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH); //????????????????????????
        cal.set(Calendar.DAY_OF_MONTH, firstDay);   //????????????????????????????????????
        cal.set(Calendar.HOUR, 0);//????????????
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();

    }

    //??????????????????????????????
    public static Date getLastDayOfMonth(Date lastDate) {
        String date = ymToString(lastDate);
        int year = Integer.parseInt(date.substring(0, 4));  //????????????????????????????????????int
        int month = Integer.parseInt(date.substring(5, 7));    //?????????????????????????????????int
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);    //????????????
        cal.set(Calendar.MONTH, month - 1);  //????????????
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  //????????????????????????
        cal.set(Calendar.DAY_OF_MONTH, lastDay);    //????????????????????????????????????
        cal.set(Calendar.HOUR, 23);//????????????
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    //??????
//    public static void main(String[] args) {
//        String month = "2021-09";
//        System.out.println(getFirstDayOfMonth(month));
//        System.out.println(getLastDayOfMonth(month));
//    }

}
