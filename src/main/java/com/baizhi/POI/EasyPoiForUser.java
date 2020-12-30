package com.baizhi.POI;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/poi")
public class EasyPoiForUser {

    @Resource
    private UserService userService;

    @RequestMapping("/export")
    public String exportExcelForUser(Integer page, Integer rows) {
        String message = null;
        List<User> users = userService.queryUserBypage(page, rows);
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生", "学生"), User.class, users);

        try {
            workbook.write(new FileOutputStream(new File("C:\\Users\\若丶随心\\Desktop\\easypoi.xls")));
            message = "ok";
        } catch (IOException e) {
            e.printStackTrace();
            message = "error";
        }
        try {
            workbook.close();
            message = "ok";
        } catch (IOException e) {
            e.printStackTrace();
            message = "error";
        }
        return message;
    }
}

