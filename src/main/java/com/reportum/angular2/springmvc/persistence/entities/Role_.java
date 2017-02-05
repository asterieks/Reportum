package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by Anna on 02.02.2017.
 */
@StaticMetamodel(Role.class)
public class Role_ {
    public static volatile SingularAttribute<Role, Long> roleId;
    public static volatile SingularAttribute<Role, String> roleName;
    public static volatile ListAttribute<Role, User > users;
}