package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    //新增
    int add(DishFlavor dishFlavor);
    List<DishFlavor> upseById(String id);
    //删除
    int del(String id);
}
