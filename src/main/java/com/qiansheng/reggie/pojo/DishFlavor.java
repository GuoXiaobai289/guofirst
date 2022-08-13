package com.qiansheng.reggie.pojo;


import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
菜品口味
 */
@Data
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;


    //菜品id
    private String dishId;


    //口味名称
    private String name;


    //口味数据list
    private String value;


    private LocalDateTime createTime;


    private LocalDateTime updateTime;


    private Long createUser;


    private Long updateUser;


    //是否删除
    private Integer isDeleted;

}
