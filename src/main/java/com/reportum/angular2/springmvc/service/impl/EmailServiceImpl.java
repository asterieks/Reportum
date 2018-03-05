package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements IEmailService {

    private final Logger DEBUG_LOG = LoggerFactory.getLogger("DEBUG_LOGGER");

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("noreply@reportum.com");
        emailSender.send(message);
        DEBUG_LOG.debug("EmailServiceImpl.sendSimpleMessage(): message sent to" + to
                + " subject " +subject + " text: " + text);
    }
}
