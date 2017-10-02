package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.configuration.security.hmac.HmacException;
import com.reportum.angular2.springmvc.dto.LoginDTO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "/authenticate",method = RequestMethod.POST)
	public User authenticate(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws HmacException{
		return authenticationService.authenticate(loginDTO,response);
	}

	@RequestMapping("/logout")
	public void logout(){
		authenticationService.logout();
	}
}