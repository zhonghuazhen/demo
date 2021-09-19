package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "用户接口",tags = "demo模块")
@RequestMapping("/test")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserByUserName",method = RequestMethod.GET)
    @ApiOperation(value = "通过用户名获取用户信息",produces = "application/json")
    @ApiImplicitParam(name = "userName",dataType = "String",value = "用户名",required = true)
    public User getUserByUserName(@RequestParam("userName") String userName){
        User user=userService.selectByName(userName);
        if (user!=null){
            return user;
        }
        return null;
    }
}
