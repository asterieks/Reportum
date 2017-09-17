package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.configuration.security.hmac.HmacException;
import com.reportum.angular2.springmvc.configuration.security.hmac.SecurityUser;
import com.reportum.angular2.springmvc.dto.LoginDTO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {
    @Mock
    private IUserService userService;

    @Mock
    private UserDetailsService userDetailsService;


    @Mock
    private HttpServletResponse request;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService = new AuthenticationService();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUserTest() throws Exception {
        authenticationService.findUser("x");
        verify(userService).findUser("x");
    }

    @Test
    public void tokenAuthenticationTest() throws Exception {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("XX"));
        UserDetails details = new SecurityUser("x","x","x", Profile.REPORTER,authorities);
        when(userDetailsService.loadUserByUsername("x")).thenReturn(details);
        authenticationService.tokenAuthentication("x");
        verify(userDetailsService).loadUserByUsername("x");

        SecurityContextHolder.clearContext();
    }

    @Test
    public void logoutCase1Test(){
        authenticationService.logout();
        verify(userService, never()).findUser("x");
    }

    @Test
    public void logoutCase2Test(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("XX"));
        UserDetails details = new SecurityUser("y","y","y", Profile.REPORTER,authorities);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        authenticationService.logout();
        verify(userService).findUser("y");

        SecurityContextHolder.clearContext();
    }

    @Test
    public void logoutCase3Test(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("XX"));
        UserDetails details = new SecurityUser("z","z","z", Profile.REPORTER,authorities);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        User user = new User();
        user.setId("123");
        when(userService.findUser("z")).thenReturn(user);

        authenticationService.logout();
        verify(userService).findUser("z");

        SecurityContextHolder.clearContext();
    }

    @Test
    public void authenticateTest() throws HmacException {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("login");
        loginDTO.setPassword("pass");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("XX"));
        SecurityUser securityUser = new SecurityUser(loginDTO.getLogin(),loginDTO.getPassword(), "fullName", Profile.REPORTER ,authorities);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getLogin(),loginDTO.getPassword());
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        when(userDetailsService.loadUserByUsername(loginDTO.getLogin())).thenReturn(securityUser);

        User user = new User();
        user.setId("login");
        user.setPassword("pass");
        user.setProfile(Profile.REPORTER);
        user.setFullName("fullName");
        user.setAuthorities("XX");
        User user1 = new User();
        user1.setId("login2");
        List<User> allUsers =new ArrayList<>();
        allUsers.add(user);
        allUsers.add(user1);
        when(userService.findAllUsers()).thenReturn(allUsers);

        User actual = authenticationService.authenticate(loginDTO, request);
        verify(authenticationManager).authenticate(authenticationToken);
        verify(userDetailsService).loadUserByUsername(loginDTO.getLogin());
        verify(userService).findAllUsers();
        assertEquals("login",actual.getId());
        assertNull(actual.getPassword());
        assertEquals("XX",actual.getAuthorities());
        assertEquals(Profile.REPORTER,actual.getProfile());

        SecurityContextHolder.clearContext();
    }
}
