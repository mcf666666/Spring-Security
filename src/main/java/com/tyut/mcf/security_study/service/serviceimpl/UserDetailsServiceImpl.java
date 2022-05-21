package com.tyut.mcf.security_study.service.serviceimpl;

import com.tyut.mcf.security_study.mapper.MenuMapper;
import com.tyut.mcf.security_study.mapper.UserMapper;
import com.tyut.mcf.security_study.pojo.LoginUser;
import com.tyut.mcf.security_study.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;

    //从数据库验证用户 而不是官方的user+控制台输出的密码
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUserName(username);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
//        ArrayList<String> list = new ArrayList<>(Arrays.asList("test"));
        List<String> perms = menuMapper.selectPermsByUserId(user.getId());
        return new LoginUser(user,perms);
    }
}