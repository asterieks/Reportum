package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HmacUserDetailsServiceTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserDetailsService hmacUserDetailsService = new HmacUserDetailsService();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameExceptionCaseTest() throws Exception {
        hmacUserDetailsService.loadUserByUsername("name");
    }

    @Test
    public void loadUserByUsernameTest() throws Exception {
        User user = new User();
        user.setId("id");
        user.setAuthorities("REPORTER");
        user.setFullName("fn");
        user.setPassword("pass");

        when(userService.findUser("name")).thenReturn(user);
        UserDetails actual = hmacUserDetailsService.loadUserByUsername("name");
        assertEquals("id",actual.getUsername());
        assertEquals(1,actual.getAuthorities().size());
    }
}
