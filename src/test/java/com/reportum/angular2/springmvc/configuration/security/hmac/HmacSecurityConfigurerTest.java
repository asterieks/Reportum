package com.reportum.angular2.springmvc.configuration.security.hmac;

import com.reportum.angular2.springmvc.service.impl.DefaultHmacRequester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


public class HmacSecurityConfigurerTest extends WebSecurityConfigurerAdapter {
    /*
    private HmacRequester hmacRequester;

    public HmacSecurityConfigurer(HmacRequester hmacRequester){
        this.hmacRequester = hmacRequester;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        HmacSecurityFilter hmacSecurityFilter = new HmacSecurityFilter(hmacRequester);
        //Trigger this filter before SpringSecurity authentication validator
        builder.addFilterBefore(hmacSecurityFilter, XAuthTokenFilter.class);
    }
    * */



//    @Mock
//    private HmacRequester hmacRequester = new DefaultHmacRequester();
//
//    @InjectMocks
//    private HmacSecurityConfigurer hmacSecurityConfigurer = new HmacSecurityConfigurer(hmacRequester);
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void configureTest() throws Exception {
//        Authentication auth = new UsernamePasswordAuthenticationToken("user1@example.com", "user1");
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(auth);
//
//        authenticationManager();
//        //hmacSecurityConfigurer.configure();
//    }
}
