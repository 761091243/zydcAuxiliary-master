package com.wangpeng.controller;

import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.UserAutosign;
import com.wangpeng.service.UserAutosignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dunn
 * @date 2021年04月16日 14:21
 */
@RestController
@RequestMapping("/Autosign")
public class UserAutosignController {

    @Autowired
    private UserAutosignService userAutosignService;


    @PostMapping("/bind")
    public SysResult bind(@RequestBody UserAutosign userAutosign){
        return userAutosignService.bind(userAutosign);
    }



}
