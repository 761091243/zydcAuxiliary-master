package com.wangpeng.config.scheduled;

import com.wangpeng.config.SysResult;
import com.wangpeng.mapper.UserAutosignLogMapper;
import com.wangpeng.mapper.UserAutosignMapper;
import com.wangpeng.pojo.UserAutosign;
import com.wangpeng.pojo.UserAutosignLog;
import com.wangpeng.service.UserAutosignService;
import com.wangpeng.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dunn
 * @date 2021年04月16日 16:05
 */
@Configuration
@EnableScheduling
public class AutoSignScheduled {

    @Autowired(required = false)
    private UserAutosignService userAutosignService;

    @Autowired(required = false)
    private UserAutosignLogMapper userAutosignLogMapper;



//    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 0 0,8 * * ? *")
    private void configureTasks() {
        System.err.println("--------执行静态定时任务时间: " + LocalDateTime.now());
        List<UserAutosign> users = userAutosignService.findSure();
        for (UserAutosign user : users) {
            // 登陆
            // 验证账号密码是否正确
            String token = RequestUtil.login(user.getUsername(), user.getPassword());
            if (StringUtils.isEmpty(token)) {
                // 更新签到日志
                updateLog(user.getId(),"签到失败！密码错误，请同步更新密码！");
                continue;
            }
            // 签到
            String reamark = RequestUtil.sign(token);
            // 更新签到日志
            if (StringUtils.isEmpty(reamark)){
                updateLog(user.getId(),"执行成功！");
            }else {
                updateLog(user.getId(),reamark);
            }

        }
    }


    // 更新签到日志
    public void updateLog(Integer userId, String explain){
        UserAutosignLog userAutosignLog = new UserAutosignLog();
        userAutosignLog.setUserId(userId);
        userAutosignLog.setDetails(explain);
        userAutosignLogMapper.insert(userAutosignLog);
    }



}

