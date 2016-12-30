package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.service.IUserReportService;
import com.reportum.angular2.springmvc.utils.beans.UserReportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IUserReportService userReportService;

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UserReportBean> createUserReport(@RequestBody UserReportBean report) {
        userReportService.saveReport(report);
        return new ResponseEntity<>(new UserReportBean(), HttpStatus.CREATED);
    }

}
