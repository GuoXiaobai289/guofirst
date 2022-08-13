package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.User;

public interface iUserService {
    User userSelByPhone(String phone);
    int userAdd(User user);
}
