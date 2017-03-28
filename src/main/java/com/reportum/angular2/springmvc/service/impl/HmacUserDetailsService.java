package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.configuration.security.hmac.SecurityUser;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HmacUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User "+username+" not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(!user.getAuthorities().isEmpty()){
            authorities.add(new SimpleGrantedAuthority(user.getAuthorities()));
        }

        return new SecurityUser(user.getId(),user.getFullName(),user.getPassword(),user.getProfile(),authorities);
    }
}
