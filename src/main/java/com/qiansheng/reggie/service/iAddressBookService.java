package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.AddressBook;

import java.util.List;

public interface iAddressBookService {
    //查询所有
    List<AddressBook> addressBookSel(String id);
    boolean addressBookAdd(AddressBook addressBook);
    //设置默认地址
    boolean isDef(String id,String userId);
    //修改查询
    AddressBook addressBookSelByid(String id);
    //删除
    int addressBookDel(String id);
    //修改
    boolean addressBookUp(AddressBook addressBook);
    //查询默认地址
    AddressBook addressBookSelByDef(String id);
}
