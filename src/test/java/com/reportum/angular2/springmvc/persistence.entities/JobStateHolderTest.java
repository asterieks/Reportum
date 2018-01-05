package com.reportum.angular2.springmvc.persistence.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JobStateHolderTest {
    private JobStateHolder jobStateHolder;

    @Before
    public void setUp(){
        jobStateHolder = new JobStateHolder();
        jobStateHolder.setJobId(1L);
        jobStateHolder.setJobName("Name");
        jobStateHolder.setStateDate(new Date());
    }

    @Test
    public void getterAndSetterTest(){
        assertEquals(new Long(1L), jobStateHolder.getJobId());
        assertEquals("Name", jobStateHolder.getJobName());
        assertNotNull(jobStateHolder.getStateDate());
    }
}
