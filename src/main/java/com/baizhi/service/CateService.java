package com.baizhi.service;

import com.baizhi.entity.Category;

import java.util.List;

public interface CateService {
    //查询所有一级类别
    public List<Category> queryAllOne(Integer page, Integer rows);

    //添加一级类别
    public String addCateOne(Category category);

    //修改一级类别
    public String updateCateOne(Category category);

    //删除一级类别
    public String delCateOne(Category category);

    //查所有一级类别
    public List<Category> queryAllOne();


    //添加二级类别
    public String addCateTwo(Category category);

    //修改二级类别
    public String updateCateTwo(Category category);

    //删除二级类别
    public String delCateTwo(Category category);

    //根据parentid查二级类别:分页
    public List<Category> queryTwoForParentId(Integer page, Integer rows, String parentId);

    //根据parentid查二级类别
    public List<Category> queryTwoForParentId(String parentId);
}
