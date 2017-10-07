package com.reportum.angular2.springmvc.configuration.security;

import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityConfigurationTest {

    @Mock
    AuthenticationManagerBuilder managerBuilder;

    @Mock
    private IUserService userService;

    @InjectMocks
    private SecurityConfiguration config = new SecurityConfiguration();

    @Test
    public void passwordEncoderTest(){
        PasswordEncoder actual = config.passwordEncoder();
        assertNotNull(actual);
    }

    @Test
    public void authenticationManagerBeanTest() throws Exception {
        User user = new User();
        user.setId("12");
        user.setPassword("pass");
        user.setProfile(Profile.MANAGER);
        List<User> list = new ArrayList<>();
        list.add(user);

        when(managerBuilder.inMemoryAuthentication()).thenReturn(new InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder>());
        when(userService.findAllUsers()).thenReturn(list);

        config.configure(managerBuilder);
        verify(managerBuilder).inMemoryAuthentication();
        verify(userService).findAllUsers();
    }
}
