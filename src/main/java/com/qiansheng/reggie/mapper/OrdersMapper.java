package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface OrdersMapper {
    //新增
    int inse(Orders orders);
    //根据时间查询订单id
    String selOIdByTime(LocalDateTime localDateTime);
}
