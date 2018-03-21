package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IUserDAO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO userDAO;

	@Override
	public User findUser(String id) {
		return userDAO.findUser(id);
	}

	@Override
	public List<User> findAllUsers() {
		return userDAO.findAllUsers();
	}

	@Override
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}

	@Override
	public void deleteUser(String userId) {
		userDAO.deleteUser(userId);
	}

	@Override
	public User prepareUser(User user) {
		user.setAuthorities("ROLE_" + user.getProfile().name());
		return user;
	}
}
