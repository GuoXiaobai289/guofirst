package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.User;
import com.qiansheng.reggie.service.iUserService;
import com.qiansheng.reggie.util.SMSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private SMSUtil smsUtil;
    @Autowired
    private iUserService userService;

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
                request.getSession().setAttribute(user.getPhone(), s);
                log.info(s);
                return R.success("发送成功！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return R.error("发送失败！");
    }

    @RequestMapping("/login")
    public R<User> log(@RequestBody Map map, HttpServletRequest request){
        log.info(map.toString());
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");
        //取出session中的验证码进行比较
        HttpSession session = request.getSession();
        String serviceCode = (String) session.getAttribute(phone);
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
            }
            if(user.getStatus()==0){
                return R.error("用户已被禁用！");
            }
            session.setAttribute("user",user);
            return R.success(user);
        }
        return R.error("验证码错误！");
    }
}
