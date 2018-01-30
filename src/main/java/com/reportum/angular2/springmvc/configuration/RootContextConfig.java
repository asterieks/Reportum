package com.reportum.angular2.springmvc.configuration;

import com.reportum.angular2.springmvc.configuration.dbconfig.AppDataSourceConfig;
import com.reportum.angular2.springmvc.configuration.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import java.util.Properties;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@EnableScheduling
@PropertySource("classpath:application.properties")
@Import({SecurityConfiguration.class, AppDataSourceConfig.class})
@ComponentScan(basePackages = "com.reportum.angular2.springmvc",
               excludeFilters = {@ComponentScan.Filter(type = ANNOTATION, value = Configuration.class),
                                 @ComponentScan.Filter(type = ANNOTATION, value = Controller.class)  })
public class RootContextConfig {
    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("mail.sender.host"));
        mailSender.setPort(Integer.valueOf(env.getProperty("mail.sender.port")));

        mailSender.setUsername(env.getProperty("mail.sender.username"));
        mailSender.setPassword(env.getProperty("mail.sender.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", env.getProperty("mail.transport.protocol"));
        props.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
        props.put("mail.debug", env.getProperty("mail.debug"));

        return mailSender;
    }
}
