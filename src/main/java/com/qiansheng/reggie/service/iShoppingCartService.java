package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.ShoppingCart;

import java.util.List;

public interface iShoppingCartService {
    //根据用户ID查询所有
    List<ShoppingCart> shopSelAll(String id);
    //添加购物车
    boolean shopAdd(ShoppingCart shoppingCart);
    //判断购物车
    ShoppingCart shopSelByUDS(ShoppingCart shoppingCart);
    //数量修改
    int shopUpNum(ShoppingCart shoppingCart);
    //通过ID删除
    int shopDel(ShoppingCart shoppingCart);
    //删除全部
    int shopDelAll(String userId);
}
