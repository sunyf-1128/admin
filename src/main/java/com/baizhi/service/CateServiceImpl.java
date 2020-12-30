package com.baizhi.service;

import com.baizhi.aspect.MyLog;
import com.baizhi.dao.CategoryMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.util.PageBegin;
import com.baizhi.util.id;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CateServiceImpl implements CateService {

    @Resource
    private CategoryMapper categoryMapper;

    //查询所有一级类别：分页
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryAllOne(Integer page, Integer rows) {

        Integer begin = PageBegin.getBegin(page, rows);

        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo("1");

        RowBounds rowBounds = new RowBounds(begin - 1, rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);

        return categories;
    }

    //添加一级类别
    @MyLog("添加一级类别")
    @Override
    public String addCateOne(Category category) {
        category.setId(id.getId());
        category.setLevels("1");
        categoryMapper.insert(category);
        return "您已成功添加类别：" + category.getName();
    }

    //修改一级类别
    @MyLog("修改一级类别")
    @Override
    public String updateCateOne(Category category) {

        categoryMapper.updateByPrimaryKeySelective(category);
        return "您已成功将类别名修改为：" + category.getName();
    }

    //删除一级类别
    @MyLog("删除一级类别")
    @Override
    public String delCateOne(Category category) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(category.getId());
        String message = null;
        System.out.println(categoryMapper.selectByExample(example).size());
        if (categoryMapper.selectByExample(example).size() != 0) {
            message = "因此一级类别下管理着二级类别，此类别不允许被删除";
        } else {
            categoryMapper.deleteByPrimaryKey(category);
            message = "您已成功删除此类别";
        }

        return message;
    }

    //查所有一级类别
    @Override
    public List<Category> queryAllOne() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelsEqualTo("1");
        List<Category> all = categoryMapper.selectByExample(categoryExample);
        return all;
    }

    //添加二级类别
    @MyLog("添加二级类别")
    @Override
    public String addCateTwo(Category category) {
        category.setId(id.getId());
        category.setLevels("2");
        categoryMapper.insertSelective(category);
        return "您已成功添加类别：" + category.getName();
    }

    //修改二级类别
    @MyLog("修改二级类别")
    @Override
    public String updateCateTwo(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
        return "您已成功将类别名修改为：" + category.getName();
    }

    //删除二级类别
    @MyLog("删除二级类别")
    @Override
    public String delCateTwo(Category category) {
        categoryMapper.deleteByPrimaryKey(category);
        return "您已成功删除此类别";
    }

    //根据parentId查询二级类别：分页
    @Override
    public List<Category> queryTwoForParentId(Integer page, Integer rows, String parentId) {
        Integer begin = PageBegin.getBegin(page, rows);

        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        RowBounds bounds = new RowBounds(begin - 1, rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, bounds);

        return categories;
    }

    //根据parentId查询二级类别
    @Override
    public List<Category> queryTwoForParentId(String parentId) {


        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);

        List<Category> categories = categoryMapper.selectByExample(example);

        return categories;
    }
}
