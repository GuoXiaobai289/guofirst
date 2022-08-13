package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.Page;
import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.Employee;
import com.qiansheng.reggie.pojo.Setmeal;
import com.qiansheng.reggie.pojo.SetmealDish;
import com.qiansheng.reggie.pojo.dto.SetmealDto;
import com.qiansheng.reggie.service.iSetmealDishService;
import com.qiansheng.reggie.service.iSetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private iSetmealService setmealService;
    @Autowired
    private iSetmealDishService setmealDishService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Setmeal>> page(int page, int pageSize, String name) {
        if (name != null) {
            name = "%" + name + "%";
        }
        List<SetmealDto> dishes = setmealService.setmealSelPage(page, pageSize, name);
        System.out.println(dishes.toString());
        if (dishes != null) {
            return R.success(new Page(dishes, setmealService.setmealNum()));
        } else {
            return R.error("查询失败！");
        }
    }

    /**
     *
     * @param setmealDto
     * @param request
     * @return
     */
    @PostMapping
    public R<String> ins(@RequestBody SetmealDto setmealDto, HttpServletRequest request) {
        //log.info(setmealDto.toString());
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        LocalDateTime now = LocalDateTime.now();
        setmealDto.setCreateTime(now);
        setmealDto.setUpdateTime(now);
        setmealDto.setCreateUser(employee.getId());
        setmealDto.setUpdateUser(employee.getId());
        boolean b = setmealService.setmealAdd(setmealDto);
        if (b) {
            return R.success("成功！");
        }
        return R.error("插入失败！");
    }

    /**
     * 修改查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> upsel(@PathVariable("id") String id){
        log.info("查询套餐："+id);
        SetmealDto setmealDto = setmealService.setmealSelById(id);
        if(setmealDto!=null){
            setmealDto.setSetmealDishes(setmealDishService.setmealDishselBySeId(id));
        }
        return R.success(setmealDto);
    }

    /**
     * 修改
     * @param setmealDto
     * @param request
     * @return
     */
    @PutMapping
    public R<String> up(@RequestBody SetmealDto setmealDto,HttpServletRequest request){
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setUpdateUser(employee.getId());
        boolean b = setmealService.setmealUp(setmealDto);
        if(b){
            return R.success("修改成功！");
        }
        return R.error("修改失败！");
    }

    /**
     * 停/启售
     * @return
     */
    @PostMapping("/status/{type}")
    public R<String > upStatus(@PathVariable("type") int type,String ids){
        String[] split = ids.split(",");
        boolean b = setmealService.setmealUpStatus(type, split);
        if(b){
            return R.success("操作成功！");
        }
        return R.error("操作失败！");
    }

    /**
     * 删除
     * @return
     */
    @DeleteMapping
    public R<String> del(String ids) throws Exception {
        String[] split = ids.split(",");
        boolean del = setmealService.setmealDel(split);
        if(del){
            return R.success("删除成功！");
        }
        return R.error("删除失败！");
    }

    /**
     * 查询套餐下的分类列表
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public R<List<SetmealDto>> list(String categoryId){
        log.info(categoryId);
        List<SetmealDto> setmealDtos = setmealService.setmealSelByCaId(categoryId);
        return R.success(setmealDtos);
    }

    /**
     * 查询套餐下的菜品
     * @param sid
     * @return
     */
    @GetMapping("/dish/{sid}")
    public R<List<SetmealDish>> dish(@PathVariable("sid") String sid){
        List<SetmealDish> setmealDishes = setmealDishService.setmealDishselBySeId(sid);
        return R.success(setmealDishes);
    }
}
