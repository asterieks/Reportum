package com.reportum.angular2.springmvc.configuration;

import com.reportum.angular2.springmvc.configuration.dbconfig.AppDataSourceConfig;
import com.reportum.angular2.springmvc.configuration.security.SecurityConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@EnableScheduling
@Import({SecurityConfiguration.class, AppDataSourceConfig.class})
@ComponentScan(basePackages = "com.reportum.angular2.springmvc",
               excludeFilters = {@ComponentScan.Filter(type = ANNOTATION, value = Configuration.class),
                                 @ComponentScan.Filter(type = ANNOTATION, value = Controller.class)  })
public class RootContextConfig {}
