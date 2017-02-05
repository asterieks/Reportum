package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, String> id;
    public static volatile SingularAttribute<User, String> fullName;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, Project > reporterList;
    public static volatile ListAttribute<User, Project> teamLeaderList;
    public static volatile ListAttribute<User, Project> managerList;
}
