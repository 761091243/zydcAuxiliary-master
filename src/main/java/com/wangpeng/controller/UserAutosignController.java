package com.wangpeng.controller;

import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.UserAutosign;
import com.wangpeng.service.UserAutosignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dunn
 * @date 2021年04月16日 14:21
 */
@RestController
@RequestMapping("/Autosign")
public class UserAutosignController {

    @Autowired
    private UserAutosignService userAutosignService;

    /**
     * 绑定
     * @param userAutosign
     * @return
     */
    @PostMapping("/bind")
    public SysResult bind(@RequestBody UserAutosign userAutosign){
        return userAutosignService.bind(userAutosign);
    }

    /**
     * 查询用户信息及历史签到
     * @return
     */
    @PostMapping("/see")
    public SysResult see(@RequestBody UserAutosign user){
        return userAutosignService.see(user.getUsername(),user.getPassword());
    }

}
