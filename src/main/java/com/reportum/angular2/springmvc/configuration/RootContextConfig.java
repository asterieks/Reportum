package com.reportum.angular2.springmvc.configuration;

import com.reportum.angular2.springmvc.configuration.dbconfig.AppDataSourceConfig;
import com.reportum.angular2.springmvc.configuration.security.SecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import java.util.Properties;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@EnableScheduling
@Import({SecurityConfiguration.class, AppDataSourceConfig.class})
@ComponentScan(basePackages = "com.reportum.angular2.springmvc",
               excludeFilters = {@ComponentScan.Filter(type = ANNOTATION, value = Configuration.class),
                                 @ComponentScan.Filter(type = ANNOTATION, value = Controller.class)  })
public class RootContextConfig {

    private final Logger DEBUG_LOG = LoggerFactory.getLogger("DEBUG_LOGGER");

    @Value("${mail.sender.host}")
    private String host;

    @Value("${mail.sender.port}")
    private String port;

    @Value("${mail.sender.username}")
    private String username;

    @Value("${mail.sender.password}")
    private String password;

    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.starttls.enable}")
    private String starttls;

    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getJavaMailSender() {
        DEBUG_LOG.debug("RootContextConfig.getJavaMailSender()");
        DEBUG_LOG.debug("host: "+ host);
        DEBUG_LOG.debug("port: "+ port);
        DEBUG_LOG.debug("username: "+ username);
        DEBUG_LOG.debug("password: "+ password);
        DEBUG_LOG.debug("protocol: "+ protocol);
        DEBUG_LOG.debug("auth: "+ auth);
        DEBUG_LOG.debug("starttls: "+ starttls);
        DEBUG_LOG.debug("debug: "+ debug);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.valueOf(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", debug);
        return mailSender;
    }

    @Configuration
    @Profile("production")
    @PropertySource("file:${spool.dir}/props/application.properties")
    static class ProdProperties {}

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:dev-application.properties")
    static class DevProperties {}
}
