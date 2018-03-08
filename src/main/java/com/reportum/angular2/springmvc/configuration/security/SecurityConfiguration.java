package com.reportum.angular2.springmvc.configuration.security;


import com.reportum.angular2.springmvc.configuration.security.hmac.HmacRequester;
import com.reportum.angular2.springmvc.configuration.security.hmac.HmacSecurityConfigurer;
import com.reportum.angular2.springmvc.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private HmacRequester hmacRequester;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js}")
                .antMatchers("/**/*.{js}")
                .antMatchers("/node_modules/**")
                .antMatchers("/static/**")
                .antMatchers("*.{ico}")
                .antMatchers("/views/**/*.{html}")
                .antMatchers("/app/**/*.{html}");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/authenticate").anonymous()
                .antMatchers("/api/**").authenticated()
            .and()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .logout().permitAll()
            .and()
                .apply(authTokenConfigurer())
            .and()
                .apply(hmacSecurityConfigurer())
            .and()
                .requiresChannel()
                .anyRequest().requiresSecure();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private HmacSecurityConfigurer hmacSecurityConfigurer(){
        return new HmacSecurityConfigurer(hmacRequester);
    }

    private XAuthTokenConfigurer authTokenConfigurer(){
        return new XAuthTokenConfigurer(authenticationService);
    }
}
