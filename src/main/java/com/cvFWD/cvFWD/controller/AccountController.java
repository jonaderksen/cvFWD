package com.cvFWD.cvFWD.controller;

import com.cvFWD.cvFWD.domain.Account;
import com.cvFWD.cvFWD.domain.UserInfo;
import com.cvFWD.cvFWD.model.InitialCvModel;
import com.cvFWD.cvFWD.model.UserInfoModel;
import com.cvFWD.cvFWD.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final
    AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity getUserinfo(@PathVariable("email") String email) {
        UserInfo result = this.accountService.getUserInfo(email);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping( value = "/initial")
    public ResponseEntity innitialCv(InitialCvModel initialCvModel) {
        Account result = this.accountService.createInitalCv(initialCvModel);
        if (result == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity updateUserinfo(UserInfoModel userInfoModel) {
        UserInfo userInfo = this.accountService.updateUserinfo(userInfoModel);
        if (userInfo == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(userInfo, HttpStatus.OK);
    }
}
