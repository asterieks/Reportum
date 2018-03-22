package com.reportum.angular2.springmvc.configuration.security;

import com.reportum.angular2.springmvc.configuration.security.hmac.HmacException;
import com.reportum.angular2.springmvc.configuration.security.hmac.HmacSigner;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.impl.AuthenticationService;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Auth token filter
 * Created by Michael DESIGAUD on 14/02/2016.
 */
public class XAuthTokenFilter extends GenericFilterBean {

    private AuthenticationService authenticationService;

    public XAuthTokenFilter(AuthenticationService authenticationService){
       this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (!uri.contains("/api") || uri.contains("/api/authenticate")){
            filterChain.doFilter(request, response);
        } else {

            try {
                Cookie jwtCookie = SecurityUtils.findJwtCookie(request);
                Assert.notNull(jwtCookie,"No jwt cookie found");

                String jwt = jwtCookie.getValue();
                String login = HmacSigner.getJwtClaim(jwt, AuthenticationService.JWT_CLAIM_LOGIN);
                Assert.notNull(login,"No login found in JWT");

                User user = this.authenticationService.findUser(login);
                Assert.notNull(user,"No user found with login: "+login);

                Assert.isTrue(HmacSigner.verifyJWT(jwt,user.getPrivateSecret()),"The Json Web Token is invalid");

                Assert.isTrue(!HmacSigner.isJwtExpired(jwt),"The Json Web Token is expired");

                String csrfHeader = request.getHeader(AuthenticationService.CSRF_CLAIM_HEADER);
                Assert.notNull(csrfHeader,"No csrf header found");

                String jwtCsrf = HmacSigner.getJwtClaim(jwt, AuthenticationService.CSRF_CLAIM_HEADER);
                Assert.notNull(jwtCsrf,"No csrf claim found in jwt");

                //Check csrf token (prevent csrf attack)
                Assert.isTrue(jwtCsrf.equals(csrfHeader));

                this.authenticationService.tokenAuthentication(login);
                filterChain.doFilter(request,response);
            } catch (HmacException | ParseException e) {
                logger.error(e.toString());
                response.setStatus(403);
            }
        }

    }
}
