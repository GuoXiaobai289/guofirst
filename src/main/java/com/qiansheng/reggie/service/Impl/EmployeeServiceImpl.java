package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.EmployeeMapper;
import com.qiansheng.reggie.pojo.Employee;
import com.qiansheng.reggie.service.iEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements iEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(Employee employee) {
        Employee selByUsername = employeeMapper.selEmployeeByUsername(employee);
        return selByUsername;
    }

    @Override
    public List<Employee> employeeList(int page, int pagesize,String name) {
        page=(page-1)*pagesize;
        return employeeMapper.seleEmployeeLimit(page,pagesize,name);
    }

    @Override
    public int employeeNumber() {
        return employeeMapper.selEmployeeNumber();
    }

    @Override
    public int employeeAdd(Employee employee) {
        return employeeMapper.addEmployee(employee);
    }

    @Override
    public int employeeUpSt(Employee employee) {
        return employeeMapper.updataEmployeeStatus(employee);
    }

    @Override
    public Employee employeeUpSel(int id) {
        return employeeMapper.updataEmployeeById(id);
    }

    @Override
    public int employeeUp(Employee employee) {
        return employeeMapper.updataEmployee(employee);
    }
}
