package com.qiansheng.reggie.service;

import com.qiansheng.reggie.pojo.Category;

import java.util.List;

public interface iCategoryService {
    List<Category> categoryPage(int page,int pagesize);
    int categoryTotal();
    int categoryAdd(Category category);
    int categoryDel(Long id);
    int categoryUpda(Category category);
    List<Category> categorySelList(int type);
}
