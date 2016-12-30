package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IUserDAO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IProjectService projectService;

	@Override
	public User findUserById(String id) {
		return userDAO.findUserById(id);
	}
}
