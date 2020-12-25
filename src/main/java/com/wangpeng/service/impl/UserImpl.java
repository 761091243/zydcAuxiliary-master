/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: UserImpl
 * Author:   Administrator
 * Date:     2020/11/13 14:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wangpeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangpeng.config.SysResult;
import com.wangpeng.mapper.UserMapper;
import com.wangpeng.pojo.User;
import com.wangpeng.service.UserService;
import com.wangpeng.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/11/13
 * @since 1.0.0
 */
@Service
public class UserImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public SysResult userAdd(User user) {
        System.out.println("开始竞价--------------------------------------");
        // 验证账号密码是否正确
        String token = RequestUtil.login(user.getUserName(), user.getUserPwd());
        if (StringUtils.isEmpty(token)){
            return new SysResult(200,"账号或密码错误",true);
        }


        return new SysResult(200,"密码正确",true);
    }
}