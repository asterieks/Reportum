package com.reportum.angular2.springmvc.controller.integrationtest;

import com.reportum.angular2.springmvc.controller.integrationtest.config.IntegrationTestConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuthenticateControllerIT extends IntegrationTestConfigurer {

    private static final String LOGOUT = "/api/logout";

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
    public void logoutWithoutHeadersTest() throws Exception {
        mockMvc.perform(
                get(LOGOUT)
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void logoutOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                get(LOGOUT)
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void authenticateWithoutHeadersTest() throws Exception {
        mockMvc.perform(
                post(AUTHENTICATE)
        )
                .andExpect(status().isBadRequest());
    }
}
