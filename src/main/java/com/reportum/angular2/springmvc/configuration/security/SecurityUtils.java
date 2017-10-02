package com.reportum.angular2.springmvc.configuration.security;

import com.reportum.angular2.springmvc.service.impl.AuthenticationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

    private SecurityUtils(){}
    /**
     * Find a cookie which contain a JWT
     * @param request current http request
     * @return Cookie found, null otherwise
     */
    public static Cookie findJwtCookie(HttpServletRequest request) {
        if(request.getCookies() == null || request.getCookies().length == 0) {
            return null;
        }
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getName().contains(AuthenticationService.JWT_APP_COOKIE)) {
                return cookie;
            }
        }
        return null;
    }
}
