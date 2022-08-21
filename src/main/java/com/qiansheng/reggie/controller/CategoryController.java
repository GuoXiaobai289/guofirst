package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.Page;
import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.Category;
import com.qiansheng.reggie.pojo.DishFlavor;
import com.qiansheng.reggie.pojo.Employee;
import com.qiansheng.reggie.pojo.User;
import com.qiansheng.reggie.service.iCategoryService;
import com.qiansheng.reggie.service.iDishFlavorService;
import com.qiansheng.reggie.service.iDishService;
import com.qiansheng.reggie.service.iSetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private iCategoryService categoryService;
    @Autowired
    private iDishService dishService;
    @Autowired
    private iDishFlavorService dishFlavorService;
    @Autowired
    private iSetmealService setmealService;

    @GetMapping("/page")
    public R<Page<Category>> page(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        log.info(page+"-----"+pageSize);
        List<Category> categories = categoryService.categoryPage(page, pageSize);
        if(categories!=null){
            Page page1 = new Page(categories, categoryService.categoryTotal());
            return R.success(page1);
        }else {
            return R.error("查询失败！");
        }
    }

    /**
     * 新增分类
     * @param category
     * @param request
     * @return
     */
    @PostMapping
    public R add(@RequestBody Category category,HttpServletRequest request){
        log.info(category.toString());
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        category.setCreateUser(employee.getId());
        category.setUpdateUser(employee.getId());
        int i = categoryService.categoryAdd(category);
        if(i==1){
            return R.success("添加成功！");
        }else {
            return R.error("添加失败！");
        }
    }

    /**
     * 删除，在进行操作时首先要判断当前分类下是否有菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public R del(long ids,int type){
        //判断类型:1为菜品分类Dish，2为套餐分类Setmeal
        if(type==1){
            //跟根据类型查找是否有餐品
            int i = dishService.dishSelByCate(ids);
            //如有结束操作
            if(i!=0){
                return R.error("无法删除：该分类下有菜品！");
            }

        }else if(type==2) {
            //查找是否有餐品
            int i = setmealService.setmealSelByCate(ids);
            //如有结束操作
            if(i!=0){
                return R.error("无法删除：该分类下有菜品！");
            }
        }
        //log.info(String.valueOf(ids)+"------"+type);
        //如没有则进行删除
        int i = categoryService.categoryDel(ids);
        if(i==1){
            return R.success("删除成功！");
        }else {
            return R.error("删除失败！");
        }
    }

    /**
     * 修改
     * @param category
     * @return
     */
    @PutMapping
    public R upD(@RequestBody Category category){
        log.info(category.toString());
        int i = categoryService.categoryUpda(category);
        if(i==1){
            return R.success("修改成功");
        }else
            return R.error("修改失败！");
    }

    /**
     * 获取分类列表
     * (@RequestParam(defaultValue = "10")的作用是给字段默认赋值
     * @return
     */
    @RequestMapping("/list")
    public R<List<Category>> list(@RequestParam(defaultValue = "10") Integer type,HttpServletRequest request){
        List<Category> categories = categoryService.categorySelList(type);
        if(categories!=null){
            return R.success(categories);
        }else {
            return R.error("查询失败！");
        }
    }
}
