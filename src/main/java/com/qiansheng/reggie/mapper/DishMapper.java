package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.DishFlavor;
import com.qiansheng.reggie.pojo.dto.DishDto;
import com.qiansheng.reggie.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper {
    //删除分类前查询是否有子类
    int selByCate(Long id);
    //分页查询
    List<DishDto> sel(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name);
    //查询总条数
    int selNum();
    //添加
    int add(Dish dish);
    //根据name查询id
    String selByName(String name);
    //修改查询
    DishDto upselByid(Long id);
    //修改
    int updat(DishDto dishDto);
    //修改status
    int upStatus(@Param("id") String id,@Param("type") int type);
    //删除
    int del(String id);
    //通过分类id获取菜品列表
    List<DishDto> selByCid(String id);
}
