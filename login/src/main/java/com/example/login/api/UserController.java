package com.example.login.api;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.constant.RedisConstant;
import com.example.common.constant.utils.Result;
import com.example.common.constant.utils.TokenSign;
import com.example.login.model.User;
import com.example.login.service.UserService;
import com.example.login.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Api(value = "用户接口",tags = "demo模块")
@RequestMapping("/test")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger= Logger.getLogger(UserController.class);

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

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ApiOperation(value = "登录",produces = "application/json")
    @ApiImplicitParams({
     @ApiImplicitParam(name = "userName",dataType = "String",value = "用户名",required = true),
     @ApiImplicitParam(name = "password",dataType = "String",value = "密码",required = true)})
    public Result getUserByUserName(@RequestParam("userName") String userName, @RequestParam("password") String password){
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_name",userName).eq("pass_word",password);
        User user=userService.getOne(queryWrapper);
        if (user!=null){
          String token= TokenSign.sign(userName,user.getUuid());
          redisTemplate.opsForValue().set(RedisConstant.USER_TOKEN+user.getUuid(),token,RedisConstant.TOKEN_EXPIRE_EXPIRE, TimeUnit.MILLISECONDS.SECONDS);
          return Result.success(token);
        }
        return Result.fail("查无此用户");
    }

    @RequestMapping(value = "/importUser",method = RequestMethod.POST)
    @ApiOperation(value = "导入用户数据",produces = "application/json")
    @ApiImplicitParam(name = "file",dataType = "File",value = "用户名",required = true)
    public Result importUser(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ImportParams params = new ImportParams();
        //表头是列名称，标题是表头上面的文字
        //表头
        params.setHeadRows(1);
        //标题
        params.setTitleRows(1);
        List<User> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),
                User.class, params);
        for (User user:result){
            System.out.println(user.toString());
        }
        return Result.success("OK");
    }

    @RequestMapping(value = "/downLoadExcel", method = RequestMethod.GET)
    public void downLoadExcel(HttpServletRequest request , HttpServletResponse response) {
        try {
            String title = "用户表"; //标题名
            String sheetName = "用户表"; //表名
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            List<User> list = userService.list(queryWrapper);
            ExcelUtils.exportExcel(list, title, sheetName, User.class, "user" + System.currentTimeMillis(), response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e.fillInStackTrace());
        }
    }
}
