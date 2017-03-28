package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.User;

import java.util.List;

public interface IUserService {

	User findUser(String id);

	List<User> findAllUsers();

	void saveUser(User user);
}
