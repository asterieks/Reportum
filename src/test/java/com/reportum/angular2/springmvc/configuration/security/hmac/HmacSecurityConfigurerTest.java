package com.reportum.angular2.springmvc.configuration.security.hmac;

import com.reportum.angular2.springmvc.service.impl.DefaultHmacRequester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpSecurity.class)
public class HmacSecurityConfigurerTest {

    @Test
    public void configureTest() throws Exception {
        HttpSecurity builder = PowerMockito.mock(HttpSecurity.class);
        HmacSecurityConfigurer securityConfigurer = new HmacSecurityConfigurer(new DefaultHmacRequester());

        securityConfigurer.configure(builder);
        Mockito.verify(builder).addFilterBefore(Mockito.any(),Mockito.any());
    }
}
