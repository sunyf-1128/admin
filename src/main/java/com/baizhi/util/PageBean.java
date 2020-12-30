package com.baizhi.util;

import java.io.Serializable;
import java.util.List;

/**
 * @author 4-孙缘飞
 * @version 1.0 2020年9月3日 下午6:19:31
 */
public class PageBean implements Serializable {

    private Integer PageCurrent;// 当前页de页码
    private static Integer PageNum = 3;// 每页显示的数据条数，根据需要修改
    private static Integer pageCount;// 总页数
    private List<?> list;// 当前页上的数据

    // 1.得到总页数
    public static Integer getPageCount(Integer Total) {// Total为查询所得数据总条数
        if (((double) Total / PageNum) > Total / PageNum)
            pageCount = (Total / PageNum) + 1;
        else
            pageCount = Total / PageNum;
        return pageCount;
    }

    // 2.得到每页起始行数
    public static Integer getBegin(Integer PageCurrent) {
        Integer Begin = (PageCurrent - 1) * PageNum + 1;
        return Begin;
    }

    // 3.得到每页结束行数
    public static Integer getEnd(Integer PageCurrent) {
        Integer End = PageCurrent * PageNum;
        return End;
    }

    public PageBean() {
        super();
    }

    // 将1.当前页（做上一页、下一页功能） 2.总页数（动态页码） 3.当前页上展示的数据 -->封装为PageBean对象
    // 传给action 存入session作用域 ，以便前端页面展示数据
    public PageBean(Integer pageCurrent, Integer pageCount, List<?> list) {
        super();
        PageCurrent = pageCurrent;
        pageCount = pageCount;
        this.list = list;
    }

    public Integer getPageCurrent() {
        return PageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        PageCurrent = pageCurrent;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        pageCount = pageCount;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean [PageCurrent=" + PageCurrent + ", pageCount="
                + pageCount + ", list=" + list + "]";
    }
}
