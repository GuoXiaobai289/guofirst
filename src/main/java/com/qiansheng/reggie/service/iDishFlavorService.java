package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.DishFlavor;
import com.qiansheng.reggie.pojo.Employee;
import com.qiansheng.reggie.pojo.dto.DishDto;

import java.util.List;

public interface iDishFlavorService {
    int dishFlavorAdd(DishFlavor dishFlavor);
    List<DishFlavor> dishFlavorSelById(String id);
    boolean dishFlavorUp(DishDto dishDto, Employee employee);
}
