package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.Dish;
import com.qiansheng.reggie.pojo.dto.SetmealDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetmealMapper {
    int selByCate(Long id);
    //分页查询
    List<SetmealDto> sel(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name);
    //查询总条数
    int selNum();
    //新增
    int inse(SetmealDto setmealDto);
    //根据name查id
    String selIdByName(String name);
    //根据ID查询信息
    SetmealDto selById(String id);
    //修改
    int up(SetmealDto setmealDto);
    //停/启售
    int upst(@Param("type") int type,@Param("id") String id);
    //判断是否停售
    int isSt(String[] ids);
    //删除
    int del(String id);
    //根据分类id查询套餐
    List<SetmealDto> selByCaId(String caid);
}
