package com.qiansheng.reggie.pojo;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;


    //类型 1 菜品分类 2 套餐分类
    private Integer type;


    //分类名称
    private String name;


    //顺序
    private Integer sort;


    //创建时间
    private LocalDateTime createTime;


    //更新时间
    private LocalDateTime updateTime;


    //创建人
    private Long createUser;


    //修改人
    private Long updateUser;


    //是否删除
    private Integer isDeleted;

}
