package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    //查询所有
    List<ShoppingCart> selAll(String id);
    //新增
    int add(ShoppingCart shoppingCart);
    //通过userid和dish/semID查询是否存在
    ShoppingCart selByUDS(ShoppingCart shoppingCart);
    //修改数量
    int upNum(ShoppingCart shoppingCart);
    //删除
    int del(ShoppingCart shoppingCart);
    //删除全部
    int delAll(String userId);
}
