package com.reportum.angular2.springmvc.configuration.security.hmac;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HmacUtilsTest {

    @Test
    public void staticFieldTest(){
        assertEquals("HmacSHA256",HmacUtils.HMAC_SHA_256);
        assertEquals("HmacSHA1",HmacUtils.HMAC_SHA_1);
        assertEquals("HmacMD5",HmacUtils.HMAC_MD5);
        assertEquals("NONE",HmacUtils.NONE);

        assertEquals("X-TokenAccess",HmacUtils.X_TOKEN_ACCESS);
        assertEquals("X-Secret",HmacUtils.X_SECRET);
        assertEquals("Authentication",HmacUtils.AUTHENTICATION);
        assertEquals("X-Digest",HmacUtils.X_DIGEST);
        assertEquals("X-Once",HmacUtils.X_ONCE);
        assertEquals("X-ISS",HmacUtils.X_ISS);
    }
}
