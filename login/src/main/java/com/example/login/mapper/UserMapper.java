package com.example.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.login.model.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper extends BaseMapper<User> {
    User selectByName(@Param("userName") String userName);
}
