package com.reportum.angular2.springmvc.configuration;

import org.junit.Test;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

public class ApplicationInitializerTest {

    private ApplicationInitializer initializer = new ApplicationInitializer();

    @Test
    public void getRootConfigClassesTest(){
        Class[] actual = initializer.getRootConfigClasses();
        assertEquals(2, actual.length);
        assertEquals("com.reportum.angular2.springmvc.configuration.dbconfig.GeneralDataSourceConfig",actual[0].getName());
        assertEquals("com.reportum.angular2.springmvc.configuration.security.SecurityConfiguration",actual[1].getName());
    }

    @Test
    public void getServletConfigClassesTest(){
        Class[] actual = initializer.getServletConfigClasses();
        assertEquals(1, actual.length);
        assertEquals("com.reportum.angular2.springmvc.configuration.WebConfiguration",actual[0].getName());
    }

    @Test
    public void getServletMappingsTest(){
        String[] actual = initializer.getServletMappings();
        assertEquals(1, actual.length);
        assertEquals("/",actual[0]);
    }

    @Test
    public void createServletApplicationContextTest(){
        WebApplicationContext actual = initializer.createRootApplicationContext();
        assertNotNull(actual);
        assertTrue(actual.getEnvironment().toString().contains("dev")||actual.getEnvironment().toString().contains("production") );
    }
}
