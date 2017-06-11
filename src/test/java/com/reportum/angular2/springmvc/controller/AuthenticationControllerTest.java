package com.reportum.angular2.springmvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reportum.angular2.springmvc.dto.LoginDTO;
import com.reportum.angular2.springmvc.service.impl.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController = new AuthenticationController();

    private MockMvc mockMvc;

    private String jsonLogin;

    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();

        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setLogin("login");
        loginDTO.setPassword("password");

        ObjectMapper objectMapper=new ObjectMapper();
        jsonLogin=objectMapper.writeValueAsString(loginDTO);
    }

    @Test
    public void authenticateTest() throws Exception{
        mockMvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonLogin))
                .andExpect(status().isOk());
        verify(authenticationService).authenticate(any(), any());
    }

    @Test
    public void logoutTest() throws Exception {
        mockMvc.perform(get("/api/logout"))
                  .andExpect(status().isOk());
        verify(authenticationService).logout();
    }
}
