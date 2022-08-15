package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.Orders;
import com.qiansheng.reggie.pojo.ShoppingCart;
import com.qiansheng.reggie.pojo.dto.OrdersDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface iOrdersService {
    //下单
    boolean OrderSubmit(Orders orders, List<ShoppingCart> shoppingCarts);
    //查询
    List<OrdersDto> OrderSelPage(String page, String pageSize,Orders orders);
    //总条数
    int OrderNu();

}
