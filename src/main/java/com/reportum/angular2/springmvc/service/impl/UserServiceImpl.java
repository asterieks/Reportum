package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IUserDAO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.wrappers.LoginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO userDAO;

	@Override
	public User findUser(String id) {
		return userDAO.findUser(id);
	}

	@Override
	public LoginWrapper getRoleByUserId(String id) {
		return userDAO.getRoleByUserId(id);
	}
}
