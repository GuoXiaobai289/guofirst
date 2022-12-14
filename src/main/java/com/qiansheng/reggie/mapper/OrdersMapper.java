package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.Orders;
import com.qiansheng.reggie.pojo.dto.OrdersDto;
import com.qiansheng.reggie.pojo.vo.OrdersVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrdersMapper {
    //新增
    int inse(Orders orders);
    //查询
    List<OrdersDto> selPage(@Param("page") int page, @Param("pageSize") int pageSize,@Param("orders") OrdersVo orders);
    //查询总条数
    int selNu();
}
