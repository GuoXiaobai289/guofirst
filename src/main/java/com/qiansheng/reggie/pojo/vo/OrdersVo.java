package com.qiansheng.reggie.pojo.vo;


import com.qiansheng.reggie.pojo.Orders;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdersVo extends Orders {

    private String userName;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;
}
