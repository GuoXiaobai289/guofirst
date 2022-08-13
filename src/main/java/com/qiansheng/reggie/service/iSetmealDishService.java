package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.SetmealDish;

import java.util.List;

public interface iSetmealDishService {
    int setmealDishAdd(SetmealDish setmealDish);
    List<SetmealDish> setmealDishselBySeId(String id);
    int setmealDishDel(String id);
}
