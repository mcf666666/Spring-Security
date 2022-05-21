package com.tyut.mcf.security_study.controller;


import com.tyut.mcf.security_study.mapper.UserMapper;
import com.tyut.mcf.security_study.pojo.User;
import com.tyut.mcf.security_study.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    UserMapper userMapper;

    @GetMapping(path = "/hello")
    @PreAuthorize("@mySecurity.hasAuthority('sys_hello')")
    public String test(){
        return "hello mcf";
    }

    @PostMapping("/post")
    public String postRequest(){
        return "POST REQUEST";
    }

    @PostMapping("/getUser")
    public ResponseResult getUserByUserName(@RequestParam("userName") String userName){
        User userInfo = userMapper.getUserByUserName(userName);

        return new ResponseResult(200,userInfo);
    }
}
