package com.reportum.angular2.springmvc.configuration.security.hmac;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class HmacExceptionTest {
    @Test
    public void constructorTest(){
        HmacException ex = new HmacException("");
        assertNotNull(ex);

        ex = new HmacException("", new RuntimeException());
        assertNotNull(ex);
    }
}
