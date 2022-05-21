package com.tyut.mcf.security_study.service;


import com.tyut.mcf.security_study.pojo.User;
import com.tyut.mcf.security_study.vo.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    public ResponseResult login(User user);

    public ResponseResult logout();
}
