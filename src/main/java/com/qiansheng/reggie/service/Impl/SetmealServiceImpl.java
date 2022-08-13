package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.SetmealMapper;
import com.qiansheng.reggie.pojo.SetmealDish;
import com.qiansheng.reggie.pojo.dto.SetmealDto;
import com.qiansheng.reggie.service.iSetmealDishService;
import com.qiansheng.reggie.service.iSetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SetmealServiceImpl implements iSetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private iSetmealDishService setmealDishService;

    @Override
    public int setmealSelByCate(Long id) {
        return setmealMapper.selByCate(id);
    }

    @Override
    public List<SetmealDto> setmealSelPage(int page, int pageSize, String name) {
        return setmealMapper.sel((page - 1) * pageSize, pageSize, name);
    }

    @Override
    public int setmealNum() {
        return setmealMapper.selNum();
    }

    @Override
    public String selIdByName(String name) {
        return setmealMapper.selIdByName(name);
    }

    @Override
    @Transactional
    public boolean setmealAdd(SetmealDto setmealDto) {
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Long createUser = setmealDto.getCreateUser();
        LocalDateTime now = LocalDateTime.now();
        //套餐插入
        int inse = setmealMapper.inse(setmealDto);
        String setmealId = this.selIdByName(setmealDto.getName());
        //套餐食品表插入
        if (inse == 1) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealId);
                setmealDish.setCreateTime(now);
                setmealDish.setUpdateTime(now);
                setmealDish.setCreateUser(createUser);
                setmealDish.setUpdateUser(createUser);
                int inse1 = setmealDishService.setmealDishAdd(setmealDish);
            }
        }
        return true;
    }

    @Override
    public SetmealDto setmealSelById(String id) {
        return setmealMapper.selById(id);
    }

    /**
     * 修改
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public boolean setmealUp(SetmealDto setmealDto) {
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        LocalDateTime updateTime = setmealDto.getUpdateTime();
        Long updateUser = setmealDto.getUpdateUser();
        //修改套餐
        setmealMapper.up(setmealDto);
        //删除套餐中的菜品
        int i = setmealDishService.setmealDishDel(setmealDto.getId());
        //添加
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
            setmealDish.setCreateUser(updateUser);
            setmealDish.setCreateTime(updateTime);
            setmealDish.setUpdateTime(updateTime);
            setmealDish.setUpdateUser(updateUser);
            int i1 = setmealDishService.setmealDishAdd(setmealDish);
            if (i1 != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean setmealUpStatus(int type, String[] ids) {
        for (String id : ids) {
            int upst = setmealMapper.upst(type, id);
        }
        return true;
    }

    @Override
    public boolean setmealIsStatus(String[] ids) {
        int st = setmealMapper.isSt(ids);
        if(st==0){
            return true;
        }else
            return false;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public boolean setmealDel(String[] ids){
        //判断是否停售
        boolean b = this.setmealIsStatus(ids);
        if(b){
            return false;
        }
        for (String id : ids) {
            setmealDishService.setmealDishDel(id);
            int del = setmealMapper.del(id);
        }
        return true;
    }

    @Override
    public List<SetmealDto> setmealSelByCaId(String caid) {
        return setmealMapper.selByCaId(caid);
    }

}
