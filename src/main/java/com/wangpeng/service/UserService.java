/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: UserService
 * Author:   Administrator
 * Date:     2020/11/13 14:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wangpeng.service;

import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.User;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/11/13
 * @since 1.0.0
 */
public interface UserService {
    SysResult userAdd(User user);

}