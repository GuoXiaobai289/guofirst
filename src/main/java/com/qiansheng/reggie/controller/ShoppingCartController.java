package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.ShoppingCart;
import com.qiansheng.reggie.pojo.User;
import com.qiansheng.reggie.pojo.dto.DishDto;
import com.qiansheng.reggie.service.iShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private iShoppingCartService shoppingCartService;

    /**
     * 查询购物车列表
     *
     * @return
     */
    @RequestMapping("/list")
    public R<List<ShoppingCart>> list(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<ShoppingCart> shoppingCarts = shoppingCartService.shopSelAll(user.getId());
        return R.success(shoppingCarts);
    }

    /**
     * 加入购物车/增加数量
     * @param shoppingCart
     * @param request
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request) {
        log.info(shoppingCart.toString());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        shoppingCart.setUserId(user.getId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        //判断是否存在
        ShoppingCart shoppingCarts = shoppingCartService.shopSelByUDS(shoppingCart);
        if (shoppingCarts == null) {
            shoppingCart.setNumber(1);
            boolean b = shoppingCartService.shopAdd(shoppingCart);
            if (b) {
                return R.success(shoppingCarts);
            }
        } else {
            Integer number = shoppingCarts.getNumber();
            number++;
            shoppingCarts.setNumber(number);
            //修改数量
            int i = shoppingCartService.shopUpNum(shoppingCarts);
            if(i==1){
                return R.success(shoppingCarts);
            }

        }
        return R.error("添加失败！");
    }

    /**
     * 减少数量
     * @param shoppingCart
     * @param request
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart,HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        shoppingCart.setUserId(user.getId());
        ShoppingCart shoppingCarts = shoppingCartService.shopSelByUDS(shoppingCart);
        Integer number = shoppingCarts.getNumber();
        if(number>1){
            number--;
            shoppingCarts.setNumber(number);
            //修改数量
            int i = shoppingCartService.shopUpNum(shoppingCarts);
            if(i==1){
                return R.success(shoppingCarts);
            }
        }else {
            //删除
            int i = shoppingCartService.shopDel(shoppingCarts);
            if(i==1){
                return R.success(shoppingCarts);
            }
        }

        return R.error("修改失败！");
    }

    /**
     * 清空购物车
     * @param request
     * @return
     */
    @DeleteMapping("/clean")
    public R del(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        shoppingCartService.shopDelAll(user.getId());
        return R.success("删除成功！");
    }
}
