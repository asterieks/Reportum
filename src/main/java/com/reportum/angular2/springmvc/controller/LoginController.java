package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.wrappers.LoginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * Created by Anna on 02.02.2017.
 */
@RestController
public class LoginController {

    @Autowired
    private IUserService userService;

    private static final Logger LOGGER = Logger.getLogger( LoginController.class.getName() );

    @RequestMapping(value = "/login/{userId:.+}", method = RequestMethod.GET)
    public ResponseEntity<LoginWrapper> getLoginUser(@PathVariable String userId) {
        LoginWrapper user= userService.getRoleByUserId(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}