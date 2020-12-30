package com.baizhi.util;

import java.io.Serializable;
import java.util.List;

/**
 * @author 4-孙缘飞
 * @version 1.0 2020年9月3日 下午6:19:31
 * @Describe 此工具类只适用于使用  下标  为开始条数的数据库；不适用于 rownum  分页形式
 */
public class PageBegin implements Serializable {

    private Integer pageCurrent;// 当前页de页码
    private static Integer pageNum;// 每页显示的数据条数，根据需要修改
    private static Integer pageCount;// 总页数
    private List<?> list;// 当前页上的数据

    // 1.得到总页数
    public static Integer getPageCount(Integer Total, Integer pageNum) {// Total为查询所得数据总条数
        if (((double) Total / pageNum) > Total / pageNum)
            pageCount = (Total / pageNum) + 1;
        else
            pageCount = Total / pageNum;
        return pageCount;
    }

    // 2.得到每页起始行数
    public static Integer getBegin(Integer PageCurrent, Integer pageNum) {

        return (PageCurrent - 1) * pageNum + 1;
    }

    // 3.得到每页结束行数
    public static Integer getEnd(Integer PageCurrent, Integer pageNum) {
        Integer End = PageCurrent * pageNum;
        return End;
    }

    public PageBegin() {
        super();
    }

    // 将1.当前页（做上一页、下一页功能） 2.总页数（动态页码） 3.当前页上展示的数据 -->封装为PageBean对象
    // 传给action 存入session作用域 ，以便前端页面展示数据
    public PageBegin(Integer pageCurrent, Integer pageCount, List<?> list) {
        super();
        pageCurrent = pageCurrent;
        pageCount = pageCount;
        this.list = list;
    }

    public Integer getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        pageCurrent = pageCurrent;
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
        return "PageBean [PageCurrent=" + pageCurrent + ", pageCount="
                + pageCount + ", list=" + list + "]";
    }
}
