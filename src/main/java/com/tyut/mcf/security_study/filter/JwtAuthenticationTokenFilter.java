package com.tyut.mcf.security_study.filter;

import com.tyut.mcf.security_study.pojo.LoginUser;
import com.tyut.mcf.security_study.utils.JwtUtil;
import com.tyut.mcf.security_study.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
//这里采用继承OncePerRequestFilter的方式来自定义过滤器，而不使用实现Filter接口的方式，原因是前一种方式可以避免请求被多次拦截
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //1.获取token
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //2.解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token非法");

        }
        //3.从redis中获取用户信息
        LoginUser userInfo = redisCache.getCacheObject("login" + userid);
        if(Objects.isNull(userInfo)){
            throw new RuntimeException("用户未登录");
        }
        //4.存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userInfo,null,userInfo.getAuthorities()));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
