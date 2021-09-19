package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.ref.WeakReference;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }
    @Test
    public void test6(){
    User user=new User();
    user.setUserName("钟先生");
    user.setUserAge(24);
    user.setUserSex(1);
    user.setPassWord("123456");
    userService.save(user);
    }
    @Test
    public void test7(){
        User user=userService.selectByName("钟先生");
        if (user!=null){
            System.out.println(user.toString());
        }else {
            System.out.println("查无此人");
        }
    }

}
