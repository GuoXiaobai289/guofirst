package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper {
    //插入
    int inse(OrderDetail orderDetail);
}
