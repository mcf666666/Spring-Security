package com.tyut.mcf.security_study.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MenuMapper {
    @Select(" SELECT " +
            " DISTINCT m.`perms`" +
            " FROM" +
            " sys_user_role ur" +
            " LEFT JOIN `sys_role` r ON ur.`role_id` = r.`id`" +
            " LEFT JOIN `sys_role_menu` rm ON ur.`role_id` = rm.`role_id`" +
            " LEFT JOIN `sys_menu` m ON m.`id` = rm.`menu_id`" +
            " WHERE" +
            " user_id = #{userid}" +
            " AND r.`status` = 0" +
            " AND m.`status` = 0")
    List<String> selectPermsByUserId(Long userid);
}
