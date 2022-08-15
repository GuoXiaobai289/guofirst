package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.OrdersMapper;
import com.qiansheng.reggie.pojo.OrderDetail;
import com.qiansheng.reggie.pojo.Orders;
import com.qiansheng.reggie.pojo.ShoppingCart;
import com.qiansheng.reggie.pojo.dto.OrdersDto;
import com.qiansheng.reggie.service.iOrderDetailService;
import com.qiansheng.reggie.service.iOrdersService;
import com.qiansheng.reggie.service.iShoppingCartService;
import com.qiansheng.reggie.util.SnowFlakeUtil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrdersServiceIMpl implements iOrdersService {

    @Autowired
    private iShoppingCartService shoppingCartService;
    @Autowired
    private iOrderDetailService orderDetailService;
    @Autowired
    private OrdersMapper orderMapper;

    @Override
    @Transactional
    public boolean OrderSubmit(Orders orders, List<ShoppingCart> shoppingCarts) {
        LocalDateTime orderTime = orders.getOrderTime();
        List<OrderDetail> orderDetails = new ArrayList();
        String ordernumber = orders.getNumber();
        AtomicInteger money=new AtomicInteger(0);
        //处理订单
          //计算金额
        for (ShoppingCart shoppingCart : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail();
            BigDecimal amount = shoppingCart.getAmount();
            Integer number = shoppingCart.getNumber();
            money.addAndGet(amount.multiply(new BigDecimal(number)).intValue());
            orderDetail.setAmount(amount);
            orderDetail.setNumber(number);
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setOrderId(ordernumber);
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setName(shoppingCart.getName());
            orderDetails.add(orderDetail);
        }
        orders.setAmount(new BigDecimal(money.get()));
          //插入订单表
        orderMapper.inse(orders);
        //处理详情
        for (OrderDetail orderDetail : orderDetails) {
            //插入订单详情表
            orderDetailService.orderDetailAdd(orderDetail);
        }
        //清空购物车
        shoppingCartService.shopDelAll(orders.getUserId());
        return true;
    }

    @Override
    public List<OrdersDto> OrderSelPage(String page, String pageSize) {
        int p= (Integer.parseInt(page)-1)*Integer.parseInt(pageSize);
        List<OrdersDto> ordersDtos = orderMapper.selPage(p, Integer.parseInt(pageSize));
        for (OrdersDto ordersDto : ordersDtos) {
            String number = ordersDto.getNumber();
            //通过订单号查询订单详情
            List<OrderDetail> orderDetails = orderDetailService.orderDetaiSelByOrder(number);
            ordersDto.setOrderDetails(orderDetails);
        }
        return ordersDtos;
    }
}
