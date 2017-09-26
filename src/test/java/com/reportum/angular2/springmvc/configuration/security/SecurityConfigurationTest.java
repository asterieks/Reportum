package com.reportum.angular2.springmvc.configuration.security;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertNotNull;

public class SecurityConfigurationTest {

    @InjectMocks
    private SecurityConfiguration config = new SecurityConfiguration();

    @Test
    public void passwordEncoderTest(){
        PasswordEncoder actual = config.passwordEncoder();
        assertNotNull(actual);
    }
}
