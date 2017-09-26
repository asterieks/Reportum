package com.reportum.angular2.springmvc.configuration.security.hmac;

import com.reportum.angular2.springmvc.service.impl.AuthenticationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HmacSigner.class)
public class HmacSecurityFilterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();


    private MockHttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HmacRequester hmacRequester;

    @InjectMocks
    private HmacSecurityFilter hmacSecurityFilter = new HmacSecurityFilter(hmacRequester);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    public void doFilterCaseInIfTest() throws IOException, ServletException {
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain).doFilter(Mockito.any(),Mockito.any());
    }

    @Test
    public void doFilterCase1CookieTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseJwtNullTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt",null);
        request.setCookies(cookie);
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseCookieEmptyTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie[] cookie = {};
        request.setCookies(cookie);
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseJwtEmptyTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","");
        request.setCookies(cookie);
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseXTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("efrweffe","userX");
        request.setCookies(cookie);
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseDigestNullTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseDigestEmptyTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCasexOnceHeaderNullTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCasexOnceHeaderEmptyTest() throws IOException, ServletException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        request.addHeader(HmacUtils.X_ONCE,"");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }


    @Test
    public void doFilterCaseSecretNullTest() throws IOException, ServletException, HmacException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        request.addHeader(HmacUtils.X_ONCE,"xx");
        request.setQueryString("rr");
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", HmacSigner.ENCODING_CLAIM_PROPERTY)).thenReturn("login");
        PowerMockito.when(HmacSigner.getJwtIss("jwtValue")).thenReturn("iss");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseTest() throws IOException, ServletException, HmacException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        request.addHeader(HmacUtils.X_ONCE,"xx");
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("jwtValue", HmacSigner.ENCODING_CLAIM_PROPERTY)).thenReturn("login");
        PowerMockito.when(HmacSigner.getJwtIss("jwtValue")).thenReturn("iss");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCaseSecretNotNullTest() throws IOException, ServletException, HmacException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        request.addHeader(HmacUtils.X_ONCE,"xx");

        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("userX", HmacSigner.ENCODING_CLAIM_PROPERTY)).thenReturn("login");
        PowerMockito.when(HmacSigner.getJwtIss("userX")).thenReturn("iss");
        Mockito.when(hmacRequester.getPublicSecret("iss")).thenReturn("secret");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCasePOSTTest() throws IOException, ServletException, HmacException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        request.addHeader(HmacUtils.X_ONCE,"xx");
        request.setMethod("POST");

        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("userX", HmacSigner.ENCODING_CLAIM_PROPERTY)).thenReturn("login");
        PowerMockito.when(HmacSigner.getJwtIss("userX")).thenReturn("iss");
        Mockito.when(hmacRequester.getPublicSecret("iss")).thenReturn("secret");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCasePUTTest() throws IOException, ServletException, HmacException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        request.addHeader(HmacUtils.X_ONCE,"xx");
        request.setMethod("PUT");

        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("userX", HmacSigner.ENCODING_CLAIM_PROPERTY)).thenReturn("login");
        PowerMockito.when(HmacSigner.getJwtIss("userX")).thenReturn("iss");
        Mockito.when(hmacRequester.getPublicSecret("iss")).thenReturn("secret");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setStatus(403);
        Mockito.verify(response).getWriter();
    }

    @Test
    public void doFilterCasePATCHTest() throws IOException, ServletException, HmacException {
        Mockito.when(hmacRequester.canVerify((HttpServletRequest) request)).thenReturn(true);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);
        Cookie cookie = new Cookie("hmac-app-jwt","userX");
        request.setCookies(cookie);
        request.addHeader(HmacUtils.X_DIGEST,"xx");
        request.addHeader(HmacUtils.X_ONCE,"xx");
        request.setMethod("PATCH");
        PowerMockito.mockStatic(HmacSigner.class);
        PowerMockito.when(HmacSigner.getJwtClaim("userX", HmacSigner.ENCODING_CLAIM_PROPERTY)).thenReturn("login");
        PowerMockito.when(HmacSigner.getJwtIss("userX")).thenReturn("iss");
        PowerMockito.when(HmacSigner.encodeMac("secret","PATCHhttp://localhostxx","login")).thenReturn("xx");
        Map<String,String> customClaims = new HashMap<>();
        customClaims.put(HmacSigner.ENCODING_CLAIM_PROPERTY, HmacUtils.HMAC_SHA_256);
        HmacToken hmacToken = Mockito.mock(HmacToken.class);
        PowerMockito.when(HmacSigner.getSignedToken("secret","iss",20,customClaims)).thenReturn(hmacToken);
        Mockito.when(hmacRequester.getPublicSecret("iss")).thenReturn("secret");
        hmacSecurityFilter.doFilter(request, response, filterChain);
        Mockito.verify(response).setHeader(HmacUtils.X_TOKEN_ACCESS,null);
        Mockito.verify(filterChain).doFilter(Mockito.any(),Mockito.any());
    }
}
