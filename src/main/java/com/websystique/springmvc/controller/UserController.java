package com.websystique.springmvc.controller;

import com.websystique.springmvc.service.IUserService;
import com.websystique.springmvc.utils.beans.UserReportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public ResponseEntity<UserReportBean> createUserReport(@RequestParam String review,
                                                                 @RequestParam String issues,
                                                                 @RequestParam String plans,
                                                                 @RequestParam String reporter) {
       //TODO check if this is needed
       /* if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getUsername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/
        //userService.saveReport(userReportBean);

        System.out.println(review.length());
        System.out.println(reporter.length());
        return new ResponseEntity<>(new UserReportBean(), HttpStatus.CREATED);
    }

}
