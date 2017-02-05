package com.reportum.angular2.springmvc.wrappers;

/**
 * Created by Anna on 02.02.2017.
 */
public class LoginWrapper {
    private String email;
    private String password;
    private String fullName;
    private String roleName;

    public LoginWrapper(String email, String password, String fullName, String roleName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roleName = roleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
