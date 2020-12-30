package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ei_user")
public class User {
    @Excel(name = "id", height = 20, width = 30)
    @Id
    private String id;

    @Excel(name = "头像", type = 2)
    @Column(name = "head_portrait")
    private String headPortrait;

    @Excel(name = "用户名")
    private String username;

    @Excel(name = "简介")
    private String intro;

    @Excel(name = "手机号")
    private String mobile;

    @Excel(name = "学分")
    private Double credit;

    @Excel(name = "账户状态")
    private String status;

    @Excel(name = "注册时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date createDate;
    @Excel(name = "省份")
    @Column(name = "area")
    private String area;
    @Excel(name = "性别")
    @Column(name = "sex")
    private String sex;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait == null ? null : headPortrait.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", headPortrait='" + headPortrait + '\'' +
                ", username='" + username + '\'' +
                ", intro='" + intro + '\'' +
                ", mobile='" + mobile + '\'' +
                ", credit=" + credit +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();

    }
}