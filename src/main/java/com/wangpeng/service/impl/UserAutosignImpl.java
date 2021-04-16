package com.wangpeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangpeng.config.SysResult;
import com.wangpeng.constant.BaseConstant;
import com.wangpeng.mapper.UserAutosignMapper;
import com.wangpeng.pojo.UserAutosign;
import com.wangpeng.service.UserAutosignService;
import com.wangpeng.utils.DateUtil;
import com.wangpeng.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Dunn
 * @date 2021年04月16日 14:28
 */
@Service
public class UserAutosignImpl implements UserAutosignService {

    @Autowired(required = false)
    private UserAutosignMapper userAutosignMapper;

    @Override
    @Transactional
    public SysResult bind(UserAutosign userAutosign) {
        // 验证账号密码是否正确
        String token = RequestUtil.login(userAutosign.getUsername(), userAutosign.getPassword());
        if (StringUtils.isEmpty(token)) {
            return new SysResult(200, "账号或密码错误", true);
        }
        // 是否已绑定
        UserAutosign userDB = userAutosignMapper.selectOne(new QueryWrapper<UserAutosign>().eq("username", userAutosign.getUsername()));
        if (userDB != null){
            return new SysResult(200,"该用户已绑定",true);
        }
        // 绑定
        userAutosign.setExpireTime(DateUtil.toDate(new Date(),3));
        userAutosignMapper.insert(userAutosign);
        // 给邀请人奖励天数
        if (null != userAutosign.getInvitationCode()) {
            UserAutosign invitationUserDB = userAutosignMapper.selectOne(new QueryWrapper<UserAutosign>().eq("username", userAutosign.getInvitationCode()));
            if (null == invitationUserDB){
                return new SysResult(200,"未找到邀请人：" + userAutosign.getInvitationCode(),true);
            }
            invitationUserDB.setExpireTime(DateUtil.toDate(invitationUserDB.getExpireTime(), BaseConstant.BIND_REWARD_TIME));
            userAutosignMapper.updateById(invitationUserDB);
        }
        return new SysResult(200,"绑定成功请返回登陆！",true);
    }

    /**
     * 查询可以执行自动签到的user
     * @return
     */
    @Override
    public List<UserAutosign> findSure() {
        return userAutosignMapper.selectList(new QueryWrapper<UserAutosign>().eq("switch_type", 1).ge("expire_time", new Date()));
    }
}
