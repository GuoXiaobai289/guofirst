package com.qiansheng.reggie.controller.util;

import com.qiansheng.reggie.pojo.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> implements Serializable {
    private List<T> records;
    private int total;
}
