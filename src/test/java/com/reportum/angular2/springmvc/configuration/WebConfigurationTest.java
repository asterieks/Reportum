package com.reportum.angular2.springmvc.configuration;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class WebConfigurationTest {

    @Mock
    private ResourceHandlerRegistry registry;

    @Mock
    private List<HttpMessageConverter<?>> converters;

    @Mock
    private ContentNegotiationConfigurer contentConfigurer;

    @Mock
    private DefaultServletHandlerConfigurer servletConfigurer;

    @Mock
    private ResourceHandlerRegistration resHandler;

    @InjectMocks
    private WebConfiguration webConfig = new WebConfiguration();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getViewResolverTest(){
        ViewResolver actual = webConfig.getViewResolver();
        assertNotNull(actual);
    }

    @Test
    public void jacksonMessageConverterTest(){
        MappingJackson2HttpMessageConverter actual = webConfig.jacksonMessageConverter();
        assertNotNull(actual);
        assertNotNull(actual.getObjectMapper());
    }

    @Test
    public void addResourceHandlersTest(){
        Mockito.when(registry.addResourceHandler("/static/**")).thenReturn(resHandler);
        webConfig.addResourceHandlers(registry);
        Mockito.verify(registry).addResourceHandler(anyString());
        Mockito.verify(resHandler).addResourceLocations("/static/");
    }

    @Test
    public void configureMessageConvertersTest(){
        webConfig.configureMessageConverters(converters);
        Mockito.verify(converters).add(any());
    }

    @Test
    public void configureContentNegotiationTest(){
        Mockito.when(contentConfigurer.favorPathExtension(false)).thenReturn(contentConfigurer);
        Mockito.when(contentConfigurer.favorParameter(true)).thenReturn(contentConfigurer);
        Mockito.when(contentConfigurer.parameterName("mediaType")).thenReturn(contentConfigurer);
        Mockito.when(contentConfigurer.ignoreAcceptHeader(true)).thenReturn(contentConfigurer);
        Mockito.when(contentConfigurer.useJaf(false)).thenReturn(contentConfigurer);
        Mockito.when(contentConfigurer.defaultContentType(MediaType.APPLICATION_JSON)).thenReturn(contentConfigurer);
        Mockito.when(contentConfigurer.mediaType("xml", MediaType.APPLICATION_XML)).thenReturn(contentConfigurer);

        webConfig.configureContentNegotiation(contentConfigurer);
        Mockito.verify(contentConfigurer).favorPathExtension(false);
        Mockito.verify(contentConfigurer).favorParameter(true);
        Mockito.verify(contentConfigurer).parameterName("mediaType");
        Mockito.verify(contentConfigurer).ignoreAcceptHeader(true);
        Mockito.verify(contentConfigurer).useJaf(false);
        Mockito.verify(contentConfigurer).defaultContentType(MediaType.APPLICATION_JSON);
        Mockito.verify(contentConfigurer).mediaType("xml", MediaType.APPLICATION_XML);
        Mockito.verify(contentConfigurer).mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Test
    public void configureDefaultServletHandlingTest(){
        webConfig.configureDefaultServletHandling(servletConfigurer);
        Mockito.verify(servletConfigurer).enable();
    }
}
