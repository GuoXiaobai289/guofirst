package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.OrderDetail;

import java.util.List;

public interface iOrderDetailService {
    int orderDetailAdd(OrderDetail orderDetail);
    //通过订单号查询
    List<OrderDetail> orderDetaiSelByOrder(String id);
}
