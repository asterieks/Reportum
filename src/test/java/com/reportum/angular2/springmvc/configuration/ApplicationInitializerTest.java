package com.reportum.angular2.springmvc.configuration;

import org.junit.Test;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

public class ApplicationInitializerTest {

    private ApplicationInitializer initializer = new ApplicationInitializer();

    @Test
    public void getRootConfigClassesTest(){
        Class[] actual = initializer.getRootConfigClasses();
        assertEquals(1, actual.length);
        assertEquals("com.reportum.angular2.springmvc.configuration.RootContextConfig",actual[0].getName());
    }

    @Test
    public void getServletConfigClassesTest(){
        Class[] actual = initializer.getServletConfigClasses();
        assertEquals(1, actual.length);
        assertEquals("com.reportum.angular2.springmvc.configuration.ServletContextConfig",actual[0].getName());
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
