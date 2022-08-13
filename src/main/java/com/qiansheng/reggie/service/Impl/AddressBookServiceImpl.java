package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.AddressBookMapper;
import com.qiansheng.reggie.pojo.AddressBook;
import com.qiansheng.reggie.service.iAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements iAddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public List<AddressBook> addressBookSel(String id) {
        return addressBookMapper.selAll(id);
    }

    @Override
    public boolean addressBookAdd(AddressBook addressBook) {
        int inse = addressBookMapper.inse(addressBook);
        if(inse==1){
            return true;
        }
        return false;
    }

    /**
     * 设置为默认地址
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean isDef(String id,String userId) {
        //取消当前用户所有默认地址
        addressBookMapper.upDef(userId);
        //设置默认地址
        addressBookMapper.upDef1(id);
        return true;
    }

    @Override
    public AddressBook addressBookSelByid(String id) {
        return addressBookMapper.selByid(id);
    }

    @Override
    public int addressBookDel(String id) {
        return addressBookMapper.del(id);
    }

    @Override
    public boolean addressBookUp(AddressBook addressBook) {
        addressBookMapper.up(addressBook);
        return true;
    }

    @Override
    public AddressBook addressBookSelByDef(String id) {
        return addressBookMapper.selByDef(id);
    }
}
