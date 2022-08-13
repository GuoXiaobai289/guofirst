package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.DishFlavorMapper;
import com.qiansheng.reggie.mapper.DishMapper;
import com.qiansheng.reggie.pojo.DishFlavor;
import com.qiansheng.reggie.pojo.Employee;
import com.qiansheng.reggie.pojo.dto.DishDto;
import com.qiansheng.reggie.service.iDishFlavorService;
import com.qiansheng.reggie.service.iDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishFlavorServiceImpl implements iDishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private iDishService dishService;

    @Override
    public int dishFlavorAdd(DishFlavor dishFlavor) {
        return dishFlavorMapper.add(dishFlavor);
    }

    @Override
    public List<DishFlavor> dishFlavorSelById(String id) {
        return dishFlavorMapper.upseById(id);
    }

    @Override
    @Transactional
    public boolean dishFlavorUp(DishDto dishDto, Employee employee) {
        List<DishFlavor> dishFlavors=new ArrayList();
        dishFlavors=dishDto.getFlavors();
        //修改菜品信息
        int i = dishService.dishUP(dishDto);
        //获取菜品id
        String dishId = dishDto.getId();
        //根据菜品id删除口味
        dishFlavorMapper.del(dishId);
        //新增口味
        LocalDateTime now = LocalDateTime.now();
        for (DishFlavor dishFlavor : dishFlavors) {
            dishFlavor.setCreateTime(now);
            dishFlavor.setUpdateTime(now);
            dishFlavor.setCreateUser(employee.getId());
            dishFlavor.setUpdateUser(employee.getId());
            dishFlavor.setDishId(dishId);
            int n=dishFlavorMapper.add(dishFlavor);
            if(n!=1){
                return false;
            }
        }
        return true;
    }


}
