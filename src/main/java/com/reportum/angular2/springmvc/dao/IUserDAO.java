package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.User;

public interface IUserDAO {

    User findUserById(String id);

}
