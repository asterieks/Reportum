package com.reportum.angular2.springmvc.persistence.entities;

import com.reportum.angular2.springmvc.utils.enums.Profile;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, String> id;
    public static volatile SingularAttribute<User, String> fullName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> authorities;
    public static volatile SingularAttribute<User, Profile> profile;
    public static volatile SingularAttribute<User, String> privateSecret;
    public static volatile SingularAttribute<User, String> publicSecret;
    public static volatile ListAttribute<User, Project > reporterList;
    public static volatile ListAttribute<User, Project> teamLeaderList;
    public static volatile ListAttribute<User, Project> managerList;
}
