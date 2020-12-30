package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/cate")
public class CateController {
    @Resource
    private CateService cateService;

    //查所有一级类别：分页
    @RequestMapping("/cateOnePage")
    public List<Category> cateOnePage(Integer page, Integer rows) {

        return cateService.queryAllOne(page, rows);
    }

    //增、删、改一级类别
    @RequestMapping("/editOne")
    public String editOne(String oper, Category category) {
        String message = null;
        if (oper.equals("add")) {
            message = cateService.addCateOne(category);
        } else if (oper.equals("del")) {
            message = cateService.delCateOne(category);
        } else {
            message = cateService.updateCateOne(category);
        }
        //System.out.println("一级：：：" + message);
        return message;
    }


    //查询所有一级类别不分页
    @RequestMapping("/queryAllOne")
    public List<Category> querAllOne() {
        return cateService.queryAllOne();
    }

    //根据parentid查询所有二级类别
    @RequestMapping("/cateTwoPage")
    public List<Category> cateTwoPage(Integer page, Integer rows, String parentId) {
        // System.out.println(parentId);
        return cateService.queryTwoForParentId(page, rows, parentId);
    }

    //根据parentid查询所有二级类别
    @RequestMapping("/cateTwoForParentId")
    public List<Category> cateTwoForParentId(String parentId) {
        // System.out.println(parentId);
        return cateService.queryTwoForParentId(parentId);
    }

    //增、删、改for二级类别
    @RequestMapping("/editTwo")
    public String editTwo(String oper, Category category) {
        String message = null;
        if (oper.equals("add")) {
            message = cateService.addCateTwo(category);
        } else if (oper.equals("del")) {
            message = cateService.delCateTwo(category);
        } else {
            message = cateService.updateCateTwo(category);
        }

        //System.out.println("2级：：：" + message);
        return message;
    }
}
