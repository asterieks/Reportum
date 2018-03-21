package com.reportum.angular2.springmvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesDTO {

    @Value("${mail.sender.host}")
    private String host;

    @Value("${mail.sender.port}")
    private String port;

    @JsonIgnore
    @Value("${mail.sender.username}")
    private String username;

    @JsonIgnore
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

    @Value("${manager.receiver.email}")
    private String managerEmail;

    @Value("${admin.receiver.email}")
    private String adminEmail;

    @Value("${job.notification.title}")
    private String jobNotificationTitle;

    @Value("${update.job.message}")
    private String updateJobMessage;

    @Value("${check.job.message}")
    private String checkJobMessage;

    @Value("${check.job.time}")
    private String checkJobTime;

    @Value("${reset.state.time}")
    private String resetStateTime;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getAuth() {
        return auth;
    }

    public String getStarttls() {
        return starttls;
    }

    public String getDebug() {
        return debug;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getJobNotificationTitle() {
        return jobNotificationTitle;
    }

    public String getUpdateJobMessage() {
        return updateJobMessage;
    }

    public String getCheckJobMessage() {
        return checkJobMessage;
    }

    public String getCheckJobTime() {
        return checkJobTime;
    }

    public String getResetStateTime() {
        return resetStateTime;
    }
}
