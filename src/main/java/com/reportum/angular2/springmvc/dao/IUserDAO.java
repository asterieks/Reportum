package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.wrappers.LoginWrapper;

public interface IUserDAO {

    User findUser(String id);

    LoginWrapper getRoleByUserId(String id);

}
