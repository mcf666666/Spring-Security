package com.tyut.mcf.security_study.service.serviceimpl;


import com.tyut.mcf.security_study.pojo.LoginUser;
import com.tyut.mcf.security_study.pojo.User;
import com.tyut.mcf.security_study.service.LoginService;
import com.tyut.mcf.security_study.utils.JwtUtil;
import com.tyut.mcf.security_study.utils.RedisCache;
import com.tyut.mcf.security_study.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {

        //1.通过AuthenticationManager的authenticate方法来进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("userName or password is wrong");
        }
        //2.使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //3.authenticate存入redis
        redisCache.setCacheObject("login"+userId,loginUser);
        //4.把token响应给前端
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("token",jwt);
        return new ResponseResult(200,"login success",objectObjectHashMap);
    }

    @Override
    public ResponseResult logout() {
        //登陆成功后，会将用户信息存入SecurityContextholder,所以可以从SecurityContextholder中获取用户信息
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) token.getPrincipal();
        redisCache.deleteObject("login"+loginUser.getUser().getId());
        return new ResponseResult(200,"logout success");
    }
}
