package com.wangpeng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author dengwangpeng
 * @dete 2020/11/12 - 20:51
 */

@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/into")
    public String to(){
        return "index";
    }
    @GetMapping("/into2")
    public String to2(){
        return "index2";
    }

    @GetMapping("/test")
    public String test(){
        return "test---测试";
    }
}
