package com.baizhi.service;

import com.baizhi.aspect.MyLog;
import com.baizhi.dao.AdminMapper;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminExample;
import com.baizhi.util.MD5Utils;
import com.baizhi.util.PageBegin;
import com.baizhi.util.id;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @MyLog("登录账户")
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, String> login(Admin admin, String code, HttpSession session) {
        //System.out.println("serviceimpl");
        HashMap<String, String> map = new HashMap<String, String>();
        if (!code.equals(session.getAttribute("code"))) {
            //System.out.println(1);
            map.put("status", "401");
            map.put("message", "验证码不正确");
        } else {
            // System.out.println(2);
            AdminExample example = new AdminExample();
            example.createCriteria().andUsernameEqualTo(admin.getUsername());
            Admin login = adminMapper.selectOneByExample(example);
            if (login == null) {
                // System.out.println(3);
                map.put("status", "401");
                map.put("message", "账号不存在");

            } else if (login != null) {
                // System.out.println(4);
                if (login.getStatus().equals("blocked")) {
                    // System.out.println(5);
                    map.put("status", "401");
                    map.put("message", "账户已停用");//MD5Utils.getPassword(admin.getPassword() + login.getSalt())
                } else if (!login.getPassword().equals(MD5Utils.getPassword(admin.getPassword() + login.getSalt()))) {
                    //System.out.println(6);
                    // System.out.println(login.getPassword());
                    // System.out.println(login.getSalt() + "----" + admin.getPassword());
                    // System.out.println(MD5Utils.getPassword(admin.getPassword() + login.getSalt()));
                    map.put("status", "401");
                    map.put("message", "密码不正确");
                } else {
                    //System.out.println(7);
                    map.put("status", "200");
                    session.setAttribute("adminLogin", login);
                }
            }
        }
        // System.out.println(map.size() + "service");
        return map;
    }

    @Override
    public List<Admin> adminPage(Integer page, Integer rows) {

        Integer begin = PageBegin.getBegin(page, rows);
        //查询条件
        AdminExample example = new AdminExample();
        //分页条件
        RowBounds bounds = new RowBounds(begin - 1, rows);

        return adminMapper.selectByExampleAndRowBounds(example, bounds);
    }

    @MyLog("添加管理员")
    @Override
    public void addAdmin(Admin admin) {

        admin.setId(id.getId());
        admin.setSalt(MD5Utils.getSalt());
        admin.setStatus("normal");
        admin.setContent("common");
        admin.setPassword(MD5Utils.getPassword(admin.getPassword() + admin.getSalt()));
        adminMapper.insert(admin);
    }

    @MyLog("修改管理员账户信息")
    @Override
    public void updateAdmin(Admin admin) {

        if (admin.getPassword() != null) {
            Admin one = adminMapper.selectByPrimaryKey(admin);
            admin.setPassword(MD5Utils.getPassword(admin.getPassword() + one.getSalt()));
        }
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @MyLog("删除管理员账户")
    @Override
    public void delAdmin(Admin admin) {
        adminMapper.deleteByPrimaryKey(admin);
    }
}
