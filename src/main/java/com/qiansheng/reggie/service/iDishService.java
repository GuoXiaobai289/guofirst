package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.dto.DishDto;
import com.qiansheng.reggie.pojo.Dish;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface iDishService {
    int dishSelByCate(Long id);
    List<DishDto> dishSelPage(int page, int pageSize, String name);
    int dishNum();
    int dishadd(Dish dish);
    String dishselByName(String name);
    DishDto dishupselByid(Long id);
    int dishUP(DishDto dishDto);
    boolean dishUpStatus(String[] ids, int type);
    boolean dishDel(String[] ids);
    List<DishDto> dishSelByCid(String id);
    //根据id查询分类
    String dishSelCaByid(String id);
}
