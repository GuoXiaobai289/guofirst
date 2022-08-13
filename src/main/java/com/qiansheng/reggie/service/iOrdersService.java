package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.Orders;
import com.qiansheng.reggie.pojo.ShoppingCart;

import java.util.List;

public interface iOrdersService {
    //下单
    boolean submit(Orders orders, List<ShoppingCart> shoppingCarts);
}
