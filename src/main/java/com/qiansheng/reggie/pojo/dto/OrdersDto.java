package com.qiansheng.reggie.pojo.dto;


import com.qiansheng.reggie.pojo.OrderDetail;
import com.qiansheng.reggie.pojo.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
