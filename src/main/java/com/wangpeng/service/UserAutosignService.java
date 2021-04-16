package com.wangpeng.service;

import com.wangpeng.config.SysResult;
import com.wangpeng.pojo.UserAutosign;

import java.util.List;

/**
 * @author Dunn
 * @date 2021年04月16日 14:27
 */
public interface UserAutosignService {
    SysResult bind(UserAutosign userAutosign);


    List<UserAutosign> findSure();

    SysResult see(String username, String password);



}
