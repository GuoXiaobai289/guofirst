package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.Employee;

import java.util.List;

public interface iEmployeeService {
    Employee login(Employee employee);
    List<Employee> employeeList(int page,int pagesize,String name);
    int employeeNumber();
    int employeeAdd(Employee employee);
    int employeeUpSt(Employee employee);
    Employee employeeUpSel(int id);
    int employeeUp(Employee employee);
}
