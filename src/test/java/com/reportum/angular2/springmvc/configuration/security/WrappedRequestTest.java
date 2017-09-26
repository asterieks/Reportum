package com.reportum.angular2.springmvc.configuration.security;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.DelegatingServletInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WrappedRequestTest {

    @Test
    public void getInputStreamTest() throws IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getInputStream()).thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream("xx".getBytes())));
        WrappedRequest wrappedRequest = new WrappedRequest(req);
        ServletInputStream actual = wrappedRequest.getInputStream();
        assertNotNull(actual);
    }

    @Test
    public void getBodyTest() throws IOException {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getInputStream()).thenReturn(new DelegatingServletInputStream(new ByteArrayInputStream("xx".getBytes())));
        WrappedRequest wrappedRequest = new WrappedRequest(req);
        String actual = wrappedRequest.getBody();
        assertEquals("xx",actual);
    }
}
