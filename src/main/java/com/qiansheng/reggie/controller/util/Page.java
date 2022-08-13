package com.qiansheng.reggie.controller.util;

import com.qiansheng.reggie.pojo.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private List<T> records;
    private int total;
}
