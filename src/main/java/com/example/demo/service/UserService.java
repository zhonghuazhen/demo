package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserService extends IService<User> {
    User selectByName(String userName);
}
