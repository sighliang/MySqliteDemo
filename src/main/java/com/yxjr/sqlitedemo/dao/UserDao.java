package com.yxjr.sqlitedemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yxjr.sqlitedemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-09 11:04:29
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
   User login(@Param("userName")String userName,@Param("password") String password);
   String getRole(String userName);
}

