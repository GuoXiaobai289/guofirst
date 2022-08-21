package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.Page;
import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.*;
import com.qiansheng.reggie.pojo.dto.DishDto;
import com.qiansheng.reggie.service.iDishFlavorService;
import com.qiansheng.reggie.service.iDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private iDishService dishService;
    @Autowired
    private iDishFlavorService dishFlavorService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页获取菜品
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info(page+"--"+pageSize+"----"+name);
        if(name!=null){
            name="%"+name+"%";
        }
        List<DishDto> dishes = dishService.dishSelPage(page, pageSize, name);
        if(dishes!=null){
            return R.success(new Page(dishes,dishService.dishNum()));
        }else {
            return R.error("查询失败！");
        }
    }

    /**
     * 根据分类获取菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> SelDishlist(String categoryId){
        List<DishDto> dishDtos =null;
        //设置redis的keys
        String dishkeys="dish_"+categoryId;
        dishDtos = (List<DishDto>) redisTemplate.opsForValue().get(dishkeys);
        if(dishDtos!=null){
            return R.success(dishDtos);
        }
        dishDtos = dishService.dishSelByCid(categoryId);
        for (DishDto dishDto : dishDtos) {
            String id = dishDto.getId();
            List<DishFlavor> dishFlavors = dishFlavorService.dishFlavorSelById(id);
            dishDto.setFlavors(dishFlavors);
        }
        redisTemplate.opsForValue().set(dishkeys,dishDtos,1L, TimeUnit.HOURS);
        return R.success(dishDtos);
    }
    /**
     * 新增菜品
     * @param dishDto
     * @param request
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody DishDto dishDto, HttpServletRequest request){
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        LocalDateTime now = LocalDateTime.now();

        Dish dish = new Dish();
        dish.setCategoryId(dishDto.getCategoryId());
        dish.setName(dishDto.getName());
        dish.setImage(dishDto.getImage());
        dish.setPrice(dishDto.getPrice());
        dish.setStatus(dishDto.getStatus());
        dish.setDescription(dishDto.getDescription());
        dish.setCreateTime(now);
        dish.setUpdateTime(now);
        dish.setCreateUser(employee.getId());
        dish.setUpdateUser(employee.getId());
        int dishadd = dishService.dishadd(dish);
        if(dishadd==1){
            List<DishFlavor> flavors=new ArrayList();
            String id=dishService.dishselByName(dishDto.getName());
            flavors=dishDto.getFlavors();
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(id);
                flavor.setCreateTime(now);
                flavor.setUpdateTime(now);
                flavor.setCreateUser(employee.getId());
                flavor.setUpdateUser(employee.getId());
                dishFlavorService.dishFlavorAdd(flavor);
            }
            //清空Redis中的数据
            String dishkeys="dish_"+dish.getCategoryId();
            redisTemplate.delete(dishkeys);
            return R.success("成功！");
        }
        return R.error("出错了！");
    }

    /**
     * 修改查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> addByid(@PathVariable("id") Long id){
        DishDto dishDto = dishService.dishupselByid(id);
        if(dishDto!=null){
            List<DishFlavor> flavors = dishFlavorService.dishFlavorSelById(dishDto.getId());
            dishDto.setFlavors(flavors);
            return R.success(dishDto);
        }
        return R.error("请求出错！");
    }

    /**
     * 修改
     * @param dishDto
     * @param request
     * @return
     */
    @PutMapping
    public R<String> up(@RequestBody DishDto dishDto,HttpServletRequest request){
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        log.info(dishDto.toString());
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setUpdateUser(employee.getId());
        //进行修改操作
        boolean b = dishFlavorService.dishFlavorUp(dishDto,employee);
        if(b){
            String dishkeys="dish_"+dishDto.getCategoryId();
            redisTemplate.delete(dishkeys);
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    /**
     * 停售/启售
     * @param type
     * @param ids
     * @return
     */
    @PostMapping("/status/{type}")
    public R<String> upStatus(@PathVariable("type") int type,String ids){
        //type=1表示启售，=0表示停售
        String[] idss = ids.split(",");
        boolean b = dishService.dishUpStatus(idss, type);
        for (String s : idss) {
            //根据idss查询分类
            String s1 = dishService.dishSelCaByid(s);
            String dishkeys="dish_"+s1;
            redisTemplate.delete(dishkeys);
        }
        if(b){
            return R.success("成功！");
        }
        return R.error("失败！");
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> del(String ids){
        //type=1表示启售，=0表示停售
        String[] idss = ids.split(",");
        boolean b = dishService.dishDel(idss);
        for (String s : idss) {
            //根据idss查询分类
            String s1 = dishService.dishSelCaByid(s);
            String dishkeys="dish_"+s1;
            redisTemplate.delete(dishkeys);
        }
        if(b){
            return R.success("成功！");
        }
        return R.error("失败！");
    }
}
