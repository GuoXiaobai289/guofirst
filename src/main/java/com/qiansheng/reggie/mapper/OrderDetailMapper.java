package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    //插入
    int inse(OrderDetail orderDetail);
    //通过order_id查询
    List<OrderDetail> selByOrder(String id);
}
