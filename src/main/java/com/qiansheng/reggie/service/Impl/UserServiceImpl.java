package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.UserMapper;
import com.qiansheng.reggie.pojo.User;
import com.qiansheng.reggie.service.iUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements iUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userSelByPhone(String phone) {
        return userMapper.selByPhone(phone);
    }

    @Override
    public int userAdd(User user) {
        return userMapper.inse(user);
    }
}
