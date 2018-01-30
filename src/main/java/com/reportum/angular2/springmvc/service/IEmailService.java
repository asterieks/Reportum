package com.reportum.angular2.springmvc.service;

public interface IEmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
