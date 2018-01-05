package com.reportum.angular2.springmvc.configuration;

import com.reportum.angular2.springmvc.configuration.dbconfig.AppDataSourceConfig;
import com.reportum.angular2.springmvc.configuration.security.SecurityConfiguration;
import com.reportum.angular2.springmvc.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@EnableScheduling
@Import({SecurityConfiguration.class, AppDataSourceConfig.class})
@ComponentScan(basePackages = "com.reportum.angular2.springmvc",
               excludeFilters = {@ComponentScan.Filter(type = ANNOTATION, value = Configuration.class),
                                 @ComponentScan.Filter(type = ANNOTATION, value = Controller.class)  })
public class RootContextConfig implements ApplicationListener<ContextStartedEvent > {

    @Autowired
    private IJobService jobService;

    @Override
    @Profile("dev")
    public void onApplicationEvent(ContextStartedEvent event) {
        jobService.checkStateAndUpdate();
    }
}
