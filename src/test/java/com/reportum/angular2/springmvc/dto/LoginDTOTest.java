package com.reportum.angular2.springmvc.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginDTOTest {

    private LoginDTO loginDTO;

    @Before
    public void setUp(){
        loginDTO=new LoginDTO();
        loginDTO.setLogin("login");
        loginDTO.setPassword("password");
    }

    @Test
    public void getterAndSetterTest(){
        assertEquals("login", loginDTO.getLogin());
        assertEquals("password", loginDTO.getPassword());
    }
}
