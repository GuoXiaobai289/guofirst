package com.qiansheng.reggie.controller;


import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.util.SseEmitterUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/sse")
public class SseController {

    /**
     * 用于创建连接
     */
    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        return SseEmitterUtil.connect(userId);
    }

    /**
     * 推送给所有人
     *
     * @param message
     * @return
     */

    @GetMapping("/push/{message}")
    public R<String> push(@PathVariable(name = "message") String message) {
        //获取连接人数
        int userCount = SseEmitterUtil.getUserCount();
        //如果无在线人数，返回
        if(userCount<1){
            return R.error("无人在线！");
        }
        SseEmitterUtil.batchSendMessage(message);
        return R.success("发送成功！");
    }

    /**
     * 发送给单个人
     *
     * @param message
     * @param userid
     * @return
     */
    @ResponseBody
    @GetMapping("/push_one/{messsage}/{userid}")
    public R<String> pushOne(@PathVariable(name = "message") String message, @PathVariable(name = "userid") String userid) {
        SseEmitterUtil.sendMessage(userid, message);
        return R.success("推送消息给" + userid);
    }

    /**
     * 关闭连接
     */
    @ResponseBody
    @GetMapping("/close/{userid}")
    public R<String> close(@PathVariable("userid") String userid) {
        SseEmitterUtil.removeUser(userid);
        return R.success("连接关闭");
    }
}
