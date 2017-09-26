package com.reportum.angular2.springmvc.configuration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ApplicationInitializerTest {

    private ApplicationInitializer initializer = new ApplicationInitializer();

    @Test
    public void getRootConfigClassesTest(){
        Class[] actual = initializer.getRootConfigClasses();
        assertEquals(1, actual.length);
        assertEquals("com.reportum.angular2.springmvc.configuration.WebConfiguration",actual[0].getName());
    }

    @Test
    public void getServletConfigClassesTest(){
        Class[] actual = initializer.getServletConfigClasses();
        assertNull(actual);
    }

    @Test
    public void getServletMappingsTest(){
        String[] actual = initializer.getServletMappings();
        assertEquals(1, actual.length);
        assertEquals("/",actual[0]);
    }
}
