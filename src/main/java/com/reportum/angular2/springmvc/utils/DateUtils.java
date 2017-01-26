package com.reportum.angular2.springmvc.utils;


import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private DateUtils(){}

    public static Date getThisWeekMondayDate(){
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return  c.getTime();
    }
}
