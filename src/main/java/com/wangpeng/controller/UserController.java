package com.wangpeng.controller;

import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.User;
import com.wangpeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengwangpeng
 * @dete 2020/11/11 - 21:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @CrossOrigin(origins = "*",maxAge = 3600) // 跨越问题
    @PostMapping("/add")
    public SysResult userAdd(@RequestBody User user){
        return userService.userAdd(user);
    }

    @GetMapping("/test")
    public String test(){
        return "test---测试";
    }


}
