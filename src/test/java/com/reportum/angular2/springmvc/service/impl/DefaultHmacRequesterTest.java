package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.configuration.security.hmac.HmacRequester;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class DefaultHmacRequesterTest {

    @Mock
    private IUserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private HmacRequester defaultHmacRequester = new DefaultHmacRequester();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsernameExceptionCaseTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/xxx");
        Boolean actual = defaultHmacRequester.canVerify(request);
        assertFalse(actual);

        when(request.getRequestURI()).thenReturn("/api/authenticate");
        actual = defaultHmacRequester.canVerify(request);
        assertFalse(actual);

        when(request.getRequestURI()).thenReturn("/api");
        actual = defaultHmacRequester.canVerify(request);
        assertTrue(actual);
    }

    @Test
    public void getPublicSecretTest() throws Exception {
        String actual = defaultHmacRequester.getPublicSecret("xxx");
        assertNull(actual);

        User user = new User();
        user.setId("id");
        user.setPublicSecret("publicSecret");

        when(userService.findUser("xxx")).thenReturn(user);
        actual = defaultHmacRequester.getPublicSecret("xxx");
        assertEquals("publicSecret", actual);
    }
}
