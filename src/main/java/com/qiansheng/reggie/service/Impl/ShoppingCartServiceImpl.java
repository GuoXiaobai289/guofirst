package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.ShoppingCartMapper;
import com.qiansheng.reggie.pojo.ShoppingCart;
import com.qiansheng.reggie.service.iShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements iShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public List<ShoppingCart> shopSelAll(String id) {
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selAll(id);
        return shoppingCarts;
    }

    @Override
    public boolean shopAdd(ShoppingCart shoppingCart) {
        int add = shoppingCartMapper.add(shoppingCart);
        if(add==1){
            return true;
        }
        return false;
    }

    @Override
    public ShoppingCart shopSelByUDS(ShoppingCart shoppingCart) {
        return shoppingCartMapper.selByUDS(shoppingCart);
    }

    @Override
    public int shopUpNum(ShoppingCart shoppingCart) {
        return shoppingCartMapper.upNum(shoppingCart);
    }

    @Override
    public int shopDel(ShoppingCart shoppingCart) {
        return shoppingCartMapper.del(shoppingCart);
    }

    @Override
    public int shopDelAll(String userId) {
        return shoppingCartMapper.delAll(userId);
    }
}
