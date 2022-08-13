package com.qiansheng.reggie.pojo.dto;


import com.qiansheng.reggie.pojo.Setmeal;
import com.qiansheng.reggie.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
