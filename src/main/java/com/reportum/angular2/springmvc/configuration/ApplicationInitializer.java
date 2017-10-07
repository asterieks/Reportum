package com.reportum.angular2.springmvc.configuration;

import com.reportum.angular2.springmvc.configuration.dbconfig.GeneralDataSourceConfig;
import com.reportum.angular2.springmvc.configuration.security.SecurityConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import static ch.qos.logback.core.util.EnvUtil.isWindows;

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
        return new Class[]   {GeneralDataSourceConfig.class, SecurityConfiguration.class};
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfiguration.class };
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}