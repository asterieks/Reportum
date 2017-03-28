package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.configuration.security.hmac.HmacRequester;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Hmac Requester service
 * Created by Michael DESIGAUD on 16/02/2016.
 */
@Service
public class DefaultHmacRequester implements HmacRequester {

    @Autowired
    private IUserService userService;

    @Override
    public Boolean canVerify(HttpServletRequest request) {
        return request.getRequestURI().contains("/api") && !request.getRequestURI().contains("/api/authenticate");
    }

    @Override
    public String getPublicSecret(String iss) {
        User user = userService.findUser(iss);
        if(user != null){
            return user.getPublicSecret();
        }
        return null;
    }
}
