package com.qiansheng.reggie.controller;

import com.alibaba.fastjson2.JSON;
import com.qiansheng.reggie.controller.util.Page;
import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.Orders;
import com.qiansheng.reggie.pojo.ShoppingCart;
import com.qiansheng.reggie.pojo.User;
import com.qiansheng.reggie.pojo.dto.OrdersDto;
import com.qiansheng.reggie.service.iOrdersService;
import com.qiansheng.reggie.service.iShoppingCartService;
import com.qiansheng.reggie.util.SnowFlakeUtil;
import com.qiansheng.reggie.util.SseEmitterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Autowired
    private iShoppingCartService shoppingCartService;
    @Autowired
    private iOrdersService ordersService;

    @PostMapping("/submit")
    public R subm(@RequestBody Orders orders, HttpServletRequest request){
        //判断厨师后台是否在线
        int userCount = SseEmitterUtil.getUserCount();
        if(userCount<1){
            return R.error("提示：已经下班了哦！");
        }
        //获取用户ID
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String id = user.getId();
        //根据用户ID查询购物车
        List<ShoppingCart> shoppingCarts = shoppingCartService.shopSelAll(id);
        //下单
        String numberid = String.valueOf(SnowFlakeUtil.getId());
        orders.setUserId(id);
        orders.setStatus(1);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPhone(user.getPhone());
        orders.setNumber(numberid);
        boolean submit = ordersService.OrderSubmit(orders, shoppingCarts);
        if(submit){
            SseEmitterUtil.batchSendMessage(JSON.toJSONString(shoppingCarts));
            return R.success("提交成功！");
        }
        return R.error("提交失败！");
    }
    @GetMapping("/userPage")
    public R<Page> list(String page, String pageSize){
        List<OrdersDto> ordersDtos = ordersService.OrderSelPage(page, pageSize);
        Page<OrdersDto> ordersPage = new Page();
        ordersPage.setRecords(ordersDtos);
        return R.success(ordersPage);
    }
}
