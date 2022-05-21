package com.tyut.mcf.security_study.security;


import com.tyut.mcf.security_study.pojo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("mySecurity")
public class TestSecurity {
    public boolean hasAuthority(String authority){
        //获取当前用户所拥有的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        //判断用户是否拥有访问此接口的权限
        return permissions.contains(authority);
    }
}
