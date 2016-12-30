package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.User;

public interface IUserService {

	User findUserById(String id);
	
}
