package com.reportum.angular2.springmvc.configuration.security.hmac;

import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SecurityUserTest {

    private SecurityUser user;
    private List<GrantedAuthority> authorities;

    @Before
    public void setup() {
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("XX"));
    }

    @Test
    public void getFullNameCaseSmallConstructorTest() throws Exception {
        user = new SecurityUser("name", "pass", authorities);
        assertNull(user.getFullName());
        assertNull(user.getProfile());
        assertEquals(1,user.getAuthorities().size());
        assertEquals("name",user.getUsername());
        assertEquals("pass",user.getPassword());
    }

    @Test
    public void getFullNameCaseBigConstructorTest() throws Exception {
        user = new SecurityUser("name", "fullName", "pass", Profile.MANAGER, authorities);
        assertEquals("fullName", user.getFullName());
        assertEquals(Profile.MANAGER, user.getProfile());
        assertEquals(1,user.getAuthorities().size());
        assertEquals("name",user.getUsername());
        assertEquals("pass",user.getPassword());
    }
}
