package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where username=#{username}")
    Employee selEmployeeByUsername(Employee employee);//LIKE '%郭%'
    @Select({
            "<script>","select * from employee","<if test='name != null'>where name LIKE #{name}</if>","LIMIT #{page},#{pagesize}", "</script>"
    })
    List<Employee> seleEmployeeLimit(@Param("page") int page, @Param("pagesize") int pagesize,@Param("name") String name);
    @Select("select count(*) from employee")
    int selEmployeeNumber();
    @Insert("insert into employee(name,username,password,phone,sex,id_number,create_time,update_time,create_user,update_user) values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    int addEmployee(Employee employee);
    //禁用状态
    @Update("update employee set status=#{status} where id=#{id}")
    int updataEmployeeStatus(Employee employee);
    //修改查询
    @Select("select id,name,username,password,phone,sex,id_number as idNumber,status,create_time as createTime,update_time as updateTime,create_user as createUser,update_user as updateUser from employee where id=#{id}")
    Employee updataEmployeeById(int id);
    //修改
    @Update("update employee set name=#{name},username=#{username},phone=#{phone},sex=#{sex},id_number=#{idNumber} where id=#{id}")
    int updataEmployee(Employee employee);
}
