package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.wrappers.LoginWrapper;

public interface IUserService {

	User findUser(String id);

	LoginWrapper getRoleByUserId(String id);
	
}
