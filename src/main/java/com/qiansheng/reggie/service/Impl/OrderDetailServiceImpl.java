package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.OrderDetailMapper;
import com.qiansheng.reggie.pojo.OrderDetail;
import com.qiansheng.reggie.service.iOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements iOrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public int orderDetailAdd(OrderDetail orderDetail) {
        return orderDetailMapper.inse(orderDetail);
    }
}
