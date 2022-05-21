package com.tyut.mcf.security_study.controller;


import com.tyut.mcf.security_study.pojo.User;
import com.tyut.mcf.security_study.service.LoginService;
import com.tyut.mcf.security_study.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }

    @GetMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
