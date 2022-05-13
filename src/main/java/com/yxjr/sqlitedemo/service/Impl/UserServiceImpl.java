package com.yxjr.sqlitedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.sqlitedemo.dao.UserDao;
import com.yxjr.sqlitedemo.entity.User;
import com.yxjr.sqlitedemo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-05-09 11:04:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

