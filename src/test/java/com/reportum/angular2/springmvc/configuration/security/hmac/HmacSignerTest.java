package com.reportum.angular2.springmvc.configuration.security.hmac;

import com.nimbusds.jose.JOSEException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static org.hsqldb.lib.StringUtil.isEmpty;
import static org.junit.Assert.*;

public class HmacSignerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void staticFieldTest(){
        assertEquals("l-lev",HmacSigner.ENCODING_CLAIM_PROPERTY);
    }

    @Test
    public void getSignedTokenTest() throws HmacException {
        Map<String, String> map =  new HashMap<>();
        map.put("l-lev","HmacSHA256");
        map.put("X-HMAC-CSRF","c55045a6-3fa9-4f39-97b9-36ac59ff04ee");
        map.put("login","asterieks@gmail.com");
        HmacToken token= HmacSigner.getSignedToken("MTIwNGYwZWYtMDM5ZS00MzM5LWJhMDgtNGFkNWQ2OTZjNTAy","asterieks@gmail.com",20,map);
        assertFalse(isEmpty(token.getJwtID()));
        assertFalse(isEmpty(token.getJwt()));
        assertFalse(isEmpty(token.getSecret()));
    }

    @Test
    public void generateSecretTest() throws HmacException {
        String token= HmacSigner.generateSecret();
        assertFalse(isEmpty(token));
    }

    @Test
    public void signJWTTest() throws JOSEException {
        Map<String, String> map =  new HashMap<>();
        map.put("l-lev","HmacSHA256");
        map.put("X-HMAC-CSRF","c55045a6-3fa9-4f39-97b9-36ac59ff04ee");
        map.put("login","asterieks@gmail.com");
        String actual = HmacSigner.signJWT("MTIwNGYwZWYtMDM5ZS00MzM5LWJhMDgtNGFkNWQ2OTZjNTAy","245430b5-465a-44ca-b054-e319a82e16ec", 20, "asterieks@gmail.com",map);
        assertFalse(isEmpty(actual));
    }

    @Test
    public void isJwtExpiredTest() throws ParseException {
        Boolean actual = HmacSigner.isJwtExpired("eyJhbGciOiJIUzI1NiJ9.eyJsLWxldiI6IkhtYWNTSEEyNTYiLCJYLUhNQUMtQ1NSRiI6ImM1NTA0NWE2LTNmYTktNGYzOS05N2I5LTM2YWM1OWZmMDRlZSIsImlzcyI6ImFzdGVyaWVrc0BnbWFpbC5jb20iLCJleHAiOjE1MDU2NzU1MjksImxvZ2luIjoiYXN0ZXJpZWtzQGdtYWlsLmNvbSIsImp0aSI6IjI0NTQzMGI1LTQ2NWEtNDRjYS1iMDU0LWUzMTlhODJlMTZlYyJ9.HSFl3WK6-RZqkznrYSP8rZmeCtCJATAD9IQrzXh1kNc");
        assertTrue(actual);
    }

    @Test
    public void isJwtExpiredCaseExceptionTest() throws ParseException {
        exception.expect(ParseException.class);
        HmacSigner.isJwtExpired("");
    }

    @Test
    public void verifyJWTTest() throws HmacException {
        Boolean actual = HmacSigner.verifyJWT("eyJhbGciOiJIUzI1NiJ9.eyJsLWxldiI6IkhtYWNTSEEyNTYiLCJYLUhNQUMtQ1NSRiI6ImM1NTA0NWE2LTNmYTktNGYzOS05N2I5LTM2YWM1OWZmMDRlZSIsImlzcyI6ImFzdGVyaWVrc0BnbWFpbC5jb20iLCJleHAiOjE1MDU2NzU1MjksImxvZ2luIjoiYXN0ZXJpZWtzQGdtYWlsLmNvbSIsImp0aSI6IjI0NTQzMGI1LTQ2NWEtNDRjYS1iMDU0LWUzMTlhODJlMTZlYyJ9.HSFl3WK6-RZqkznrYSP8rZmeCtCJATAD9IQrzXh1kNc",
                "MTIwNGYwZWYtMDM5ZS00MzM5LWJhMDgtNGFkNWQ2OTZjNTAy");
        assertTrue(actual);
    }

    @Test
    public void verifyJWTCaseExceptionTest() throws HmacException {
        exception.expect(HmacException.class);
        HmacSigner.verifyJWT("1kNc", "MTIwNGYwZWYtMDM5ZS00MzM5LWJhMDgtNGFkNWQ2OTZjNTAy");
    }

    @Test
    public void getJwtClaimTest() throws HmacException {
        String actual = HmacSigner.getJwtClaim("eyJhbGciOiJIUzI1NiJ9.eyJsLWxldiI6IkhtYWNTSEEyNTYiLCJYLUhNQUMtQ1NSRiI6ImM1NTA0NWE2LTNmYTktNGYzOS05N2I5LTM2YWM1OWZmMDRlZSIsImlzcyI6ImFzdGVyaWVrc0BnbWFpbC5jb20iLCJleHAiOjE1MDU2NzU1MjksImxvZ2luIjoiYXN0ZXJpZWtzQGdtYWlsLmNvbSIsImp0aSI6IjI0NTQzMGI1LTQ2NWEtNDRjYS1iMDU0LWUzMTlhODJlMTZlYyJ9.HSFl3WK6-RZqkznrYSP8rZmeCtCJATAD9IQrzXh1kNc",
                "MTIwNGYwZWYtMDM5ZS00MzM5LWJhMDgtNGFkNWQ2OTZjNTAy");
        assertNull(actual);
    }

    @Test
    public void getJwtClaimCaseExceptionTest() throws HmacException {
        exception.expect(HmacException.class);
        HmacSigner.getJwtClaim("1kNc", "MTIwNGYwZWYtMDM5ZS00MzM5LWJhMDgtNGFkNWQ2OTZjNTAy");
    }

    @Test
    public void getJwtIssTest() throws HmacException {
        String actual = HmacSigner.getJwtIss("eyJhbGciOiJIUzI1NiJ9.eyJsLWxldiI6IkhtYWNTSEEyNTYiLCJYLUhNQUMtQ1NSRiI6ImM1NTA0NWE2LTNmYTktNGYzOS05N2I5LTM2YWM1OWZmMDRlZSIsImlzcyI6ImFzdGVyaWVrc0BnbWFpbC5jb20iLCJleHAiOjE1MDU2NzU1MjksImxvZ2luIjoiYXN0ZXJpZWtzQGdtYWlsLmNvbSIsImp0aSI6IjI0NTQzMGI1LTQ2NWEtNDRjYS1iMDU0LWUzMTlhODJlMTZlYyJ9.HSFl3WK6-RZqkznrYSP8rZmeCtCJATAD9IQrzXh1kNc");
        assertEquals("asterieks@gmail.com",actual);
    }

    @Test
    public void getJwtIssCaseExceptionTest() throws HmacException {
        exception.expect(HmacException.class);
        HmacSigner.getJwtIss("1kNc");
    }

    @Test
    public void encodeMacCaseExceptionTest() throws HmacException {
        exception.expect(HmacException.class);
        HmacSigner.encodeMac("eyJhbGciOiJIUzI1NiJ9.eyJsLWxldiI6IkhtYWNTSEEyNTYiLCJYLUhNQUMtQ1NSRiI6ImM1NTA0NWE2LTNmYTktNGYzOS05N2I5LTM2YWM1OWZmMDRlZSIsImlzcyI6ImFzdGVyaWVrc0BnbWFpbC5jb20iLCJleHAiOjE1MDU2NzU1MjksImxvZ2luIjoiYXN0ZXJpZWtzQGdtYWlsLmNvbSIsImp0aSI6IjI0NTQzMGI1LTQ2NWEtNDRjYS1iMDU0LWUzMTlhODJlMTZlYyJ9.HSFl3WK6-RZqkznrYSP8rZmeCtCJATAD9IQrzXh1kNc",
                "","");
    }

    @Test
    public void encodeMacTest() throws HmacException {
        String actual = HmacSigner.encodeMac("NjFiMGYxODMtNzEwYy00YjIxLTkwZGItNmNjMTIyYzNjY2Rm",
                "GEThttp://localhost:8081/api/users/asterieks@gmail.com/projects2017-09-17T20:17:29.190Z","HmacSHA256");
        assertEquals("7c04e92f64bafe95ca1be15adde31501dfa11c054b50f116f88703d423c61dbe",actual);
    }
}
