package com.reportum.angular2.springmvc.utils;


import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private DateUtils(){}

    public static int getDayOfMonth(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
