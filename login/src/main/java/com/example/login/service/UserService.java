package com.example.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.login.model.User;

public interface UserService extends IService<User> {
    User selectByName(String userName);
}
