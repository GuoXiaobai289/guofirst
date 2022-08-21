package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.pojo.dto.DishDto;
import com.qiansheng.reggie.mapper.DishMapper;
import com.qiansheng.reggie.pojo.Dish;
import com.qiansheng.reggie.service.iDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements iDishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public int dishSelByCate(Long id) {
        return dishMapper.selByCate(id);
    }

    @Override
    public List<DishDto> dishSelPage(int page, int pageSize, String name) {
        return dishMapper.sel((page-1)*pageSize,pageSize,name);
    }

    @Override
    public int dishNum() {
        return dishMapper.selNum();
    }

    @Override
    public int dishadd(Dish dish) {
        return dishMapper.add(dish);
    }

    @Override
    public String dishselByName(String name) {
        return dishMapper.selByName(name);
    }

    @Override
    public DishDto dishupselByid(Long id) {
        return dishMapper.upselByid(id);
    }

    @Override
    public int dishUP(DishDto dishDto) {
        return dishMapper.add(dishDto);
    }

    @Override
    @Transactional
    public boolean dishUpStatus(String[] ids, int type) {
        for (String id : ids) {
            dishMapper.upStatus(id, type);
        }
        return true;
    }

    @Override
    public boolean dishDel(String[] ids) {
        for (String id : ids) {
            int del = dishMapper.del(id);
        }
        return true;
    }

    @Override
    public List<DishDto> dishSelByCid(String id) {
        return dishMapper.selByCid(id);
    }

    @Override
    public String dishSelCaByid(String id) {
        return dishMapper.selCaByid(id);
    }
}
