package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.Page;
import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.Employee;
import com.qiansheng.reggie.service.iEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private iEmployeeService employeeService;

    /**
     * 登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //使用MD5对密码进行加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        log.info(password);
        //根据用户提交的用户名进行查询
        Employee login = employeeService.login(employee);
        if(login==null){
            return R.error("用户不存在");
        }
        if(!login.getPassword().equals(password)){
                return R.error("密码错误！");
        }
        if(login.getStatus()==0){
            return R.error("该用户已被禁用！");
        }
        request.getSession().setAttribute("employee",login);
        return R.success(login);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("logout")
    public R<Employee> logout(HttpServletRequest request){
        request.getSession()
                .removeAttribute("employee");
        return R.success(null);
    }

    /**
     * 员工信息分页查询
     * @param request
     * @return
     */
    @RequestMapping("/page")
    public R<Page> page(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String name=request.getParameter("name");
        if(name!=null){
            name="%"+name+"%";
        }
        List<Employee> employees = employeeService.employeeList(page, pageSize,name);
        return R.success(new Page(employees,employeeService.employeeNumber()));
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<Employee> add(@RequestBody Employee employee,HttpServletRequest request){
        LocalDateTime now = LocalDateTime.now();
        employee.setCreateTime(now);
        employee.setUpdateTime(now);
        Employee employee1 = (Employee) request.getSession().getAttribute("employee");
        Long id=employee1.getId();
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        //设置初始值密码
        String pass="123456";
        employee.setPassword(DigestUtils.md5DigestAsHex(pass.getBytes(StandardCharsets.UTF_8)));
        int i = employeeService.employeeAdd(employee);
        if(i==1){
            return R.success(employee);
        }else {
            return R.error("插入失败！");
        }
    }

    /**
     * 禁用员工状态
     * @param employee
     * @return
     */
    @PutMapping
    public R jingyong(@RequestBody Employee employee){
        int i = employeeService.employeeUpSt(employee);
        if(i==1){
            return R.success("禁用成功！");
        }else {
            return R.error("操作失败！");
        }
    }

    /**
     * 修改查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> updateSel(@PathVariable("id") int id){
        Employee employee = employeeService.employeeUpSel(id);
        if(employee!=null){
            return R.success(employee);
        }else {
            return R.error("查询失败！");
        }
    }

    /**
     * 禁用员工状态
     * @param employee
     * @return
     */
    @PutMapping("/up")
    public R update(@RequestBody Employee employee){
        int i = employeeService.employeeUp(employee);
        if(i==1){
            return R.success("禁用成功！");
        }else {
            return R.error("操作失败！");
        }
    }
}
