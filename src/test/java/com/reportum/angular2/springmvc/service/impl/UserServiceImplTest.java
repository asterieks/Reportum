package com.reportum.angular2.springmvc.service.impl;


import com.reportum.angular2.springmvc.dao.IUserDAO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class UserServiceImplTest {
    @Mock
    private IUserDAO userDAO;

    @InjectMocks
    private IUserService userService=new UserServiceImpl();

    private User user;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUser(){
        userService.findUser("id");
        verify(userDAO).findUser("id");
    }

    @Test
    public void findAllUsersTest(){
        userService.findAllUsers();
        verify(userDAO).findAllUsers();
    }

    @Test
    public void saveUserTest(){
        userService.saveUser(user);
        verify(userDAO).saveUser(user);
    }
}
