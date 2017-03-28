package com.reportum.angular2.springmvc.configuration.security.hmac;


import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Security spring security user
 * Created by Michael DESIGAUD on 15/02/2016.
 */
public class SecurityUser extends User {

    private String fullName;

    private Profile profile;

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUser(String id, String fullName, String password, Profile profile, Collection<? extends GrantedAuthority> authorities) {
        super(id, password, authorities);
        this.fullName = fullName;
        this.profile = profile;
    }

    public String getFullName() {
        return fullName;
    }

    public Profile getProfile() {
        return profile;
    }
}
