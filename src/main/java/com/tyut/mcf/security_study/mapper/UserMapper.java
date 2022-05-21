package com.tyut.mcf.security_study.mapper;

import com.tyut.mcf.security_study.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Select("SELECT * FROM sys_user WHERE user_name = #{userName}")
    @Results({
            @Result(column = "user_name",property = "userName"),
            @Result(column = "nick_name",property = "nickName")
    })
    User getUserByUserName(@Param("userName") String userName);

}
