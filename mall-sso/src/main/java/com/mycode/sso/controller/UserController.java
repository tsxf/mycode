package com.mycode.sso.controller;

import com.mycode.user.IUserCoreService;
import com.mycode.user.dto.UserLoginRequest;
import com.mycode.user.dto.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 蛮小江
 * 2018/7/3 9:59
 */
@RestController
public class UserController {
    @Autowired
    IUserCoreService userCoreService;

    @PostMapping("/login")
    public ResponseEntity doLogin(String username,String password){
        UserLoginRequest request = new UserLoginRequest();
        request.setUserName(username);
        request.setPassword(password);
        UserLoginResponse resopnse = userCoreService.login(request);
        return ResponseEntity.ok(resopnse);
    }

}
