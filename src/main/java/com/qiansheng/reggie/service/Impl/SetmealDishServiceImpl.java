package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.SetmealDishMapper;
import com.qiansheng.reggie.pojo.SetmealDish;
import com.qiansheng.reggie.service.iSetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl implements iSetmealDishService {
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public int setmealDishAdd(SetmealDish setmealDish) {
        return setmealDishMapper.inse(setmealDish);
    }

    @Override
    public List<SetmealDish> setmealDishselBySeId(String id) {
        return setmealDishMapper.selBySeId(id);
    }

    @Override
    public int setmealDishDel(String id) {
        return setmealDishMapper.del(id);
    }
}
