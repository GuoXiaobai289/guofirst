package com.qiansheng.reggie.service.Impl;

import com.qiansheng.reggie.mapper.CategoryMapper;
import com.qiansheng.reggie.pojo.Category;
import com.qiansheng.reggie.service.iCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements iCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> categoryPage(int page, int pagesize) {
        page=(page-1)*pagesize;
        return categoryMapper.seleByLimit(page,pagesize);
    }

    @Override
    public int categoryTotal() {
        return categoryMapper.seleNumber();
    }

    @Override
    public int categoryAdd(Category category) {
        return categoryMapper.inSert(category);
    }

    @Override
    public int categoryDel(Long id) {
        return categoryMapper.del(id);
    }

    @Override
    public int categoryUpda(Category category) {
        return categoryMapper.updat(category);
    }

    @Override
    public List<Category> categorySelList(int type) {
        return categoryMapper.sellist(type);
    }
}
