package com.reportum.angular2.springmvc.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class DateUtilsTest {

    private Calendar c = Calendar.getInstance();

    @Before
    public void setUp(){
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    @Test
    public void getThisWeekMondayDateTest(){
        assertEquals(DateUtils.getThisWeekMondayDate(), c.getTime());
    }

}