package com.example.login.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user")
@ApiModel(value = "User对象",description = "用户表")
public class User {

    @ExcelIgnore
    @TableId(value = "uuid",type = IdType.UUID)
    private String uuid;

    @Excel(name = "用户名*")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ExcelIgnore
    @ApiModelProperty(value = "密码")
    private String passWord;

    @Excel(name = "性别*",replace = {"男_0", "女_1"})
    @ApiModelProperty(value = "性别")
    private Integer userSex;

    @Excel(name = "年龄*")
    @ApiModelProperty(value = "年龄")
    private Integer userAge;

    @Excel(name = "创建时间*")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Excel(name = "更新时间*")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
