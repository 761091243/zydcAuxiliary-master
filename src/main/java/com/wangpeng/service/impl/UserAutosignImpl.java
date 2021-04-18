package com.wangpeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangpeng.config.SysResult;
import com.wangpeng.constant.BaseConstant;
import com.wangpeng.mapper.UserAutosignLogMapper;
import com.wangpeng.mapper.UserAutosignMapper;
import com.wangpeng.pojo.UserAutosign;
import com.wangpeng.pojo.UserAutosignLog;
import com.wangpeng.service.UserAutosignService;
import com.wangpeng.utils.DateUtil;
import com.wangpeng.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dunn
 * @date 2021年04月16日 14:28
 */
@Service
public class UserAutosignImpl implements UserAutosignService {

    @Autowired(required = false)
    private UserAutosignMapper userAutosignMapper;
    @Autowired(required = false)
    private UserAutosignLogMapper userAutosignLogMapper;


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
        if (userDB != null) {
            return new SysResult(200, "该用户已绑定!", true);
        }
        // 绑定
        userAutosign.setExpireTime(DateUtil.toDate(new Date(), 3));
        userAutosignMapper.insert(userAutosign);
        // 给邀请人奖励天数
        if (!StringUtils.isEmpty(userAutosign.getInvitationCode())) {
            UserAutosign invitationUserDB = userAutosignMapper.selectOne(new QueryWrapper<UserAutosign>().eq("username", userAutosign.getInvitationCode()));
            if (null == invitationUserDB) {
                return new SysResult(200, "未找到邀请人：" + userAutosign.getInvitationCode(), true);
            }
            invitationUserDB.setExpireTime(DateUtil.toDate(invitationUserDB.getExpireTime(), BaseConstant.BIND_REWARD_TIME));
            userAutosignMapper.updateById(invitationUserDB);
        }
        return new SysResult(200, "绑定成功！", true);
    }

    /**
     * 查询可以执行自动签到的user
     *
     * @return
     */
    @Override
    public List<UserAutosign> findSure() {
        return userAutosignMapper.selectList(new QueryWrapper<UserAutosign>().eq("switch_type", 1).ge("expire_time", new Date()));
    }

    @Override
    public SysResult see(String username, String password) {
        // 验证账号密码是否正确
        String token = RequestUtil.login(username, password);
        if (StringUtils.isEmpty(token)) {
            return new SysResult(200, "账号或密码错误", true);
        }

        // 查询用户信息
        UserAutosign userDB = userAutosignMapper.selectOne(new QueryWrapper<UserAutosign>().eq("username", username));
        if (null == userDB){
            return new SysResult(200,"请先绑定用户！",true);
        }
        // 查询历史签到
        List<UserAutosignLog> logs = userAutosignLogMapper.selectList(new QueryWrapper<UserAutosignLog>().eq("user_id", userDB.getId()).orderByDesc("sign_time").last("limit 0,7"));
        Map<String, Object> map = new HashMap<>();
        map.put("user",userDB);
        map.put("logs",logs);
        return new SysResult(200,"查询成功!",map);
    }
}
