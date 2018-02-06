package com.reportum.angular2.springmvc.configuration;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import static ch.qos.logback.core.util.EnvUtil.isWindows;
//replace above method

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String ENVIRONMENT = isWindows() ? "dev" : "production";

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext context = super.createRootApplicationContext();
        ((ConfigurableEnvironment) context.getEnvironment()).setActiveProfiles(ENVIRONMENT);
        return context;
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]   {RootContextConfig.class};
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletContextConfig.class };
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}