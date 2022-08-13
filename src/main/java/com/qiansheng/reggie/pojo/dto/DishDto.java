package com.qiansheng.reggie.pojo.dto;


import com.qiansheng.reggie.pojo.Dish;
import com.qiansheng.reggie.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
