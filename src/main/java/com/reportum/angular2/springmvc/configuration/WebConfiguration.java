package com.reportum.angular2.springmvc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.reportum.angular2.springmvc")
public class WebConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

		ObjectMapper mapper = new ObjectMapper();
		//Registering Hibernate4Module to support lazy objects
		mapper.registerModule(new Hibernate5Module());

		messageConverter.setObjectMapper(mapper);
		return messageConverter;

	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//Here we add our custom-configured HttpMessageConverter
		converters.add(jacksonMessageConverter());
		super.configureMessageConverters(converters);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true).useJaf(false)
				.defaultContentType(MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}