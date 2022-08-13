package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.Dish;
import com.qiansheng.reggie.pojo.dto.SetmealDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface iSetmealService {
    int setmealSelByCate(Long id);
    List<SetmealDto> setmealSelPage(int page, int pageSize, String name);
    int setmealNum();
    String selIdByName(String name);
    boolean setmealAdd(SetmealDto setmealDto);
    SetmealDto setmealSelById(String id);
    //修改
    boolean setmealUp(SetmealDto setmealDto);
    //停/启售
    boolean setmealUpStatus(int type,String[] id);
    //判断是否停售
    boolean setmealIsStatus(String[] ids);
    //删除
    boolean setmealDel(String[] id) throws Exception;
    //根据category_id查询
    List<SetmealDto> setmealSelByCaId(String caid);
}
