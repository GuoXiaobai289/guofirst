package com.qiansheng.reggie.controller;

import com.qiansheng.reggie.controller.util.R;
import com.qiansheng.reggie.pojo.AddressBook;
import com.qiansheng.reggie.pojo.User;
import com.qiansheng.reggie.service.iAddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private iAddressBookService addressBookService;

    @RequestMapping("/list")
    public R<List<AddressBook>> list(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<AddressBook> addressBooks = addressBookService.addressBookSel(user.getId().toString());
        return R.success(addressBooks);
    }

    @PostMapping
    public R<String> inse(@RequestBody AddressBook addressBook, HttpServletRequest request){
        log.info(addressBook.toString());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LocalDateTime now = LocalDateTime.now();
        addressBook.setCreateTime(now);
        addressBook.setUpdateTime(now);
        addressBook.setCreateUser(user.getId());
        addressBook.setUpdateUser(user.getId());
        addressBook.setUserId(user.getId());
        boolean b = addressBookService.addressBookAdd(addressBook);
        if (b) {
            return R.success("添加成功！");
        }
        return R.error("添加失败！");
    }

    @PutMapping("/default")
    public R<AddressBook> def(@RequestBody AddressBook addressBook,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(addressBook.getId()!=null){
            //设置默认地址
            boolean def = addressBookService.isDef(addressBook.getId(),user.getId().toString());
            if(def){
                return R.success(null);
            }
        }
        return R.error("修改失败！");
    }

    /**
     * 修改查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> upSel(@PathVariable("id") String id){
        AddressBook addressBook = addressBookService.addressBookSelByid(id);
        if(addressBook!=null){
            return R.success(addressBook);
        }
        return R.error("错误！");
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> del(String ids){
        addressBookService.addressBookDel(ids);
        return R.success("删除成功！");
    }

    /**
     * 修改
     * @param addressBook
     * @param request
     * @return
     */
    @PutMapping
    public R<String> up(@RequestBody AddressBook addressBook,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBook.setUpdateUser(user.getId());
        boolean b = addressBookService.addressBookUp(addressBook);
        if(b){
            return R.success("修改成功！");
        }
        return R.error("修改失败！");
    }

    /**
     * 查询默认地址
     * @param request
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> seldef(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        //查询默认地址
        AddressBook addressBook1 = addressBookService.addressBookSelByDef(user.getId());
        return R.success(addressBook1);
    }
}
