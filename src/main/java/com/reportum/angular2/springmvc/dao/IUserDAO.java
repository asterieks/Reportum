package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.User;

import java.util.List;

public interface IUserDAO {

    User findUser(String id);

    List<User> findAllUsers();

    void saveUser(User user);

    void deleteUser(String userId);
}
