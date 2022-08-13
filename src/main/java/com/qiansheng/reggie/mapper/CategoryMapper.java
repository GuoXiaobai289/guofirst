package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通过Mapper配置文件配值sql
 */
@Mapper
public interface CategoryMapper {
    //分页查询
    List<Category> seleByLimit(@Param("page") int page,@Param("pagesize") int pagesize);
    //查询总页数
    int seleNumber();
    //新增
    int inSert(Category category);
    //删除
    int del(Long id);
    //修改
    int updat(Category category);
    //获取分类列表
    List<Category> sellist(int type);
}
