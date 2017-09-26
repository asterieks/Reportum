package com.reportum.angular2.springmvc.configuration.security;

import com.nimbusds.jose.JOSEException;
import com.reportum.angular2.springmvc.configuration.security.hmac.HmacException;
import com.reportum.angular2.springmvc.configuration.security.hmac.HmacSigner;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.impl.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HmacSigner.class)
public class XAuthTokenFilterTest {

    private MockHttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private XAuthTokenFilter xAuthTokenFilter = new XAuthTokenFilter(authenticationService);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    public void doFilterCaseInIfTest() throws IOException, ServletException {
        request.setRequestURI("/");
        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain).doFilter(request,response);

        request.setRequestURI("/api/authenticate");
        Mockito.clearInvocations(filterChain);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain).doFilter(request,response);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCase1CookieTest() throws IOException, ServletException {
        request.setRequestURI("/api");
        xAuthTokenFilter.doFilter(request, response, filterChain);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCase2CookieTest() throws IOException, ServletException {
        Cookie[] cookie = {};
        request.setRequestURI("/api");
        request.setCookies(cookie);
        xAuthTokenFilter.doFilter(request, response, filterChain);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCase3CookieTest() throws IOException, ServletException {
        Cookie cookie = new Cookie("attrName","userX");
        request.setRequestURI("/api");
        request.setCookies(cookie);
        xAuthTokenFilter.doFilter(request, response, filterChain);
    }

    @Test (expected = IllegalArgumentException.class )
    public void doFilterCaseLoginTest() throws IOException, ServletException, JOSEException, HmacException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn(null);

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");
        request.setRequestURI("/api");
        request.setCookies(cookie);
        xAuthTokenFilter.doFilter(request, response, filterChain);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCase1UserTest() throws IOException, ServletException, JOSEException, HmacException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");
        request.setRequestURI("/api");
        request.setCookies(cookie);

        xAuthTokenFilter.doFilter(request, response, filterChain);
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCase2UserTest() throws IOException, ServletException, JOSEException, HmacException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        User user =new User();
        Mockito.when(authenticationService.findUser("login")).thenReturn(user);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(authenticationService).findUser("login");
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCaseVerifyJwtTest() throws IOException, ServletException, JOSEException, HmacException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");
        PowerMockito.when(HmacSigner.verifyJWT("jwtValue", "prSec")).thenReturn(true);

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        User user =new User();
        user.setPrivateSecret("prSec");
        Mockito.when(authenticationService.findUser("login")).thenReturn(user);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(authenticationService).findUser("login");
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCaseIsExpiredTest() throws IOException, ServletException, JOSEException, HmacException, ParseException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");
        PowerMockito.when(HmacSigner.verifyJWT("jwtValue", "prSec")).thenReturn(true);
        PowerMockito.when(HmacSigner.isJwtExpired("jwtValue")).thenReturn(false);

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        User user =new User();
        user.setPrivateSecret("prSec");
        Mockito.when(authenticationService.findUser("login")).thenReturn(user);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(authenticationService).findUser("login");
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCaseIsExpiredTrueTest() throws IOException, ServletException, JOSEException, HmacException, ParseException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");
        PowerMockito.when(HmacSigner.verifyJWT("jwtValue", "prSec")).thenReturn(true);
        PowerMockito.when(HmacSigner.isJwtExpired("jwtValue")).thenReturn(true);

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        User user =new User();
        user.setPrivateSecret("prSec");
        Mockito.when(authenticationService.findUser("login")).thenReturn(user);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(authenticationService).findUser("login");
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCaseHeaderTest() throws IOException, ServletException, JOSEException, HmacException, ParseException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");
        PowerMockito.when(HmacSigner.verifyJWT("jwtValue", "prSec")).thenReturn(true);
        PowerMockito.when(HmacSigner.isJwtExpired("jwtValue")).thenReturn(false);

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        request.addHeader(AuthenticationService.CSRF_CLAIM_HEADER,"csrfHeader");
        User user =new User();
        user.setPrivateSecret("prSec");
        Mockito.when(authenticationService.findUser("login")).thenReturn(user);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(authenticationService).findUser("login");
    }

    @Test (expected = IllegalArgumentException.class)
    public void doFilterCaseJwtClaimTest() throws IOException, ServletException, JOSEException, HmacException, ParseException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");
        PowerMockito.when(HmacSigner.verifyJWT("jwtValue", "prSec")).thenReturn(true);
        PowerMockito.when(HmacSigner.isJwtExpired("jwtValue")).thenReturn(false);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue",AuthenticationService.CSRF_CLAIM_HEADER)).thenReturn("jwtCsrf");

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        request.addHeader(AuthenticationService.CSRF_CLAIM_HEADER,"csrfHeader");
        User user =new User();
        user.setPrivateSecret("prSec");
        Mockito.when(authenticationService.findUser("login")).thenReturn(user);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(authenticationService).findUser("login");
    }

    @Test
    public void doFilterCaseLastTest() throws IOException, ServletException, JOSEException, HmacException, ParseException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenReturn("login");
        PowerMockito.when(HmacSigner.verifyJWT("jwtValue", "prSec")).thenReturn(true);
        PowerMockito.when(HmacSigner.isJwtExpired("jwtValue")).thenReturn(false);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue",AuthenticationService.CSRF_CLAIM_HEADER)).thenReturn("jwtCsrf");

        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        request.addHeader(AuthenticationService.CSRF_CLAIM_HEADER,"jwtCsrf");
        User user =new User();
        user.setPrivateSecret("prSec");
        Mockito.when(authenticationService.findUser("login")).thenReturn(user);

        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(authenticationService).findUser("login");
        Mockito.verify(authenticationService).tokenAuthentication("login");
    }

    @Test
    public void doFilterCaseCatchTest() throws IOException, ServletException, JOSEException, HmacException, ParseException {
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", AuthenticationService.JWT_CLAIM_LOGIN)).thenThrow(HmacException.class);
        Cookie cookie = new Cookie("hmac-app-jwt","jwtValue");

        request.setRequestURI("/api");
        request.setCookies(cookie);
        xAuthTokenFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
    }
}
