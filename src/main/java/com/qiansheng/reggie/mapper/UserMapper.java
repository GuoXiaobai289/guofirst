package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //查询用户是否存在
    User selByPhone(String phone);
    //注册
    int inse(User user);
}
