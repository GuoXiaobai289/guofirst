package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.User;
import com.qiansheng.reggie.service.iUserService;
import com.qiansheng.reggie.util.SMSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private SMSUtil smsUtil;
    @Autowired
    private iUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @RequestMapping("/sendMsg")
    public R<String> SMS(@RequestBody User user, HttpServletRequest request){
        if(user.getPhone()!=null){
            try {
                //生成四位验证码
                String s = smsUtil.NumberFour();
                //发送验证码
                //smsUtil.Main(s,user.getPhone());
                //将验证码存入redis并设置过期时间为60秒
                log.info("将验证码存入Redis:"+s);
                redisTemplate.opsForValue().set(user.getPhone(),s,60l, TimeUnit.SECONDS);
                //request.getSession().setAttribute(user.getPhone(), s);

                return R.success("发送成功！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.error("发送失败！");
    }

    /**
     * 用户登录
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public R<User> log(@RequestBody Map map, HttpServletRequest request){
        log.info(map.toString());
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        HttpSession session = request.getSession();
        //取出session中的验证码进行比较
        log.info("对比验证码");
        String serviceCode = (String) redisTemplate.opsForValue().get(phone);
        //String serviceCode = (String) session.getAttribute(phone);
        if(serviceCode==null){
            return R.error("请先进行短信验证！");
        }
        if(serviceCode.equals(code)){
            //判断用户是否注册
            User user = userService.userSelByPhone(phone);
            if(user==null){
                //没有注册，进行注册
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                int i = userService.userAdd(user);
                //查询userId
                user = userService.userSelByPhone(phone);
            }
            if(user.getStatus()==0){
                return R.error("用户已被禁用！");
            }
            session.setAttribute("user",user);
            return R.success(user);
        }
        return R.error("验证码错误！");
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return R.success("已退出");
    }
}
