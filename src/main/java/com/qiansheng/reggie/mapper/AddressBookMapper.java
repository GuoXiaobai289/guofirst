package com.qiansheng.reggie.mapper;

import com.qiansheng.reggie.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    //查询所有
    List<AddressBook> selAll(String id);
    //插入地址
    int inse(AddressBook addressBook);
    //默认地址全部设置为0
    void upDef(String userId);
    //修改默认地址1
    void upDef1(String id);
    //修改查询
    AddressBook selByid(String id);
    //修改
    int up(AddressBook addressBook);
    //删除
    int del(String id);
    //查询默认地址
    AddressBook selByDef(String id);
}
