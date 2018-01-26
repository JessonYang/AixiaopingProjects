package com.weslide.lovesmallscreen.exchange.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by YY on 2018/1/23.
 */
public class DateFormatUtil {

    private static SimpleDateFormat simpleDateFormat;
    
    public static String formatDateShow(long preDateMils,long curDateMils){
        long distanceMils = curDateMils - preDateMils;
        if (distanceMils >= 24 * 60 * 60 * 1000) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
            return simpleDateFormat.format(new Date(preDateMils));
        } else if (!isSameDay(preDateMils, curDateMils)) {
            simpleDateFormat = new SimpleDateFormat("昨天 HH:mm");
            return simpleDateFormat.format(new Date(preDateMils));
        } else if (isSameDay(preDateMils, curDateMils) && distanceMils > 1 * 60 * 60 * 1000) {
            simpleDateFormat = new SimpleDateFormat("今天 HH:mm");
            return simpleDateFormat.format(new Date(preDateMils));
        } else {
            return distanceMils/1000/60 + "分钟前";
        }
    }

    public static boolean isSameDay(long time1, long time2) {
        Calendar calen = Calendar.getInstance();
        calen.setTimeInMillis(time1);
        int day1 = calen.get(Calendar.DAY_OF_YEAR);
        calen.setTimeInMillis(time2);
        int day2 = calen.get(Calendar.DAY_OF_YEAR);
        return day1 == day2;
    }
}
