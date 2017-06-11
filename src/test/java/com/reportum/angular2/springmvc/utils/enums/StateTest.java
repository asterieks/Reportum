package com.reportum.angular2.springmvc.utils.enums;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StateTest {

    @Test
    public void getValueTest(){
        assertEquals("Reviewed", State.REVIEWED.getValue());
        assertEquals("Reported", State.REPORTED.getValue());
        assertEquals("Delayed", State.DELAYED.getValue());
    }
}
