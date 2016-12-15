package com.websystique.springmvc.service.impl;

import com.websystique.springmvc.dao.IUserDAO;
import com.websystique.springmvc.service.IUserService;
import com.websystique.springmvc.utils.EntityUtils;
import com.websystique.springmvc.utils.beans.UserReportBean;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {

	private IUserDAO userDAO;

	@Override
	public void saveReport(UserReportBean userReportBean) {
		userDAO.saveReport(EntityUtils.createAndReturnUserReportEntity(userReportBean));
	}
}
