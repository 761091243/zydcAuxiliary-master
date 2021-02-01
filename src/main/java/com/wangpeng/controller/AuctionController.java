/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: AuctionController
 * Author:   Administrator
 * Date:     2020/11/14 12:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wangpeng.controller;

import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.AuctionVO;
import com.wangpeng.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈房源竞价〉
 *
 * @author Administrator
 * @create 2020/11/14
 * @since 1.0.0
 */
@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired(required = false)
    private AuctionService auctionService;

    //    @CrossOrigin(origins = "*",maxAge = 3600) // 跨越问题
    @PostMapping("/start")
    public SysResult start(@RequestBody AuctionVO auctionVO) {
        return auctionService.start(auctionVO);
    }

    // 收藏夹竞标
    @CrossOrigin(origins = "*", maxAge = 3600) // 跨越问题
    @PostMapping("/favorite")
    public SysResult favorite(@RequestBody AuctionVO auctionVO) {
//        System.out.println(1/0);
        return auctionService.favorite(auctionVO);
    }

    // 登录保存token到redis
    @CrossOrigin(origins = "*", maxAge = 3600) // 跨越问题
    @PostMapping("/login")
    public SysResult login(@RequestBody AuctionVO auctionVO) {
        return auctionService.login(auctionVO);
    }

}