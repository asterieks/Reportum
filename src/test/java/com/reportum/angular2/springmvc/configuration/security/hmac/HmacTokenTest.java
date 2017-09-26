package com.reportum.angular2.springmvc.configuration.security.hmac;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HmacTokenTest {

    private HmacToken token;

    @Before
    public void setUp(){
        token = new HmacToken("id","secret", "jwt");
    }

    @Test
    public void getTest(){
        assertEquals("id",token.getJwtID());
        assertEquals("secret",token.getSecret());
        assertEquals("jwt",token.getJwt());
    }
}
