package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    //插入
    int inse(SetmealDish setmealDish);
    //查询
    List<SetmealDish> selBySeId(String id);
    //删除
    int del(String id);
}
