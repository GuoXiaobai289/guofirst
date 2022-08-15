package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.Orders;
import com.qiansheng.reggie.pojo.dto.OrdersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrdersMapper {
    //新增
    int inse(Orders orders);
    //根据时间查询订单id
    String selOIdByTime(LocalDateTime localDateTime);
    //查询
    List<OrdersDto> selPage(@Param("page") int page, @Param("pageSize") int pageSize);
}
