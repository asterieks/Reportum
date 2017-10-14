package com.reportum.angular2.springmvc.controller.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reportum.angular2.springmvc.configuration.WebConfiguration;
import com.reportum.angular2.springmvc.configuration.security.SecurityConfiguration;
import com.reportum.angular2.springmvc.configuration.security.WrappedRequest;
import com.reportum.angular2.springmvc.configuration.security.hmac.HmacException;
import com.reportum.angular2.springmvc.configuration.security.hmac.HmacSigner;
import com.reportum.angular2.springmvc.configuration.security.hmac.HmacUtils;
import com.reportum.angular2.springmvc.dto.LoginDTO;
import org.apache.commons.io.Charsets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SecurityConfiguration.class, WebConfiguration.class})
public class IntegrationTestConfig {

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    protected static final String X_SECRET = HmacUtils.X_SECRET;
    protected static final String WWW_AUTHENTICATE = HttpHeaders.WWW_AUTHENTICATE;
    protected static final String X_HMAC_CSRF = "X-HMAC-CSRF";
    protected static final String X_ONCE = HmacUtils.X_ONCE;
    protected static final String X_DIGEST = HmacUtils.X_DIGEST;
    protected String publicSecret;
    protected String wwwAuthenticateValue;
    protected String hmacValue;
    protected String xOnceValue;
    protected Cookie jwtCookie;

    private static final String LOGOUT = "/api/logout";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("asterieks@gmail.com");
        loginDTO.setPassword("1");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonLogin = objectMapper.writeValueAsString(loginDTO);

        MockHttpServletResponse response = mockMvc
                .perform(
                        post("/api/authenticate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonLogin))
                .andReturn().getResponse();

        jwtCookie = response.getCookies()[0];
        publicSecret = response.getHeader(X_SECRET);
        wwwAuthenticateValue = response.getHeader(WWW_AUTHENTICATE);
        hmacValue = response.getHeader(X_HMAC_CSRF);
        xOnceValue = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.msXX")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc.perform(
                get(LOGOUT)
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(get(LOGOUT)))
                        .cookie(jwtCookie)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void logoutWithoutAnyHeadersTest() throws Exception {
        mockMvc.perform(
                get(LOGOUT)
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void logoutWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                get(LOGOUT)
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    protected String mockUpClientDigest(MockHttpServletRequestBuilder requestBuilder) throws HmacException, IOException {
        MockHttpServletRequest request = requestBuilder.buildRequest(context.getServletContext());
        WrappedRequest wrappedRequest = new WrappedRequest(request);
        String url = request.getRequestURL().toString();
        url = addExtraInfoToUrl(url, request.getQueryString());
        String encoding = HmacSigner.getJwtClaim(jwtCookie.getValue(), HmacSigner.ENCODING_CLAIM_PROPERTY);
        String message = buildMessage(request, wrappedRequest, url, xOnceValue);
        return HmacSigner.encodeMac(publicSecret, message, encoding);
    }

    private String addExtraInfoToUrl(String url, String queryString) throws UnsupportedEncodingException {
        if (queryString != null) {
            return url + "?" + URLDecoder.decode(queryString, Charsets.UTF_8.displayName());
        }
        return url;
    }

    private String buildMessage(HttpServletRequest request, WrappedRequest wrappedRequest, String url, String xOnceHeader) throws IOException {
        if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod()) || "PATCH".equals(request.getMethod())) {
            return request.getMethod().concat(wrappedRequest.getBody()).concat(url).concat(xOnceHeader);
        }
        return request.getMethod().concat(url).concat(xOnceHeader);
    }
}
