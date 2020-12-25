package com.wangpeng.config;


import com.wangpeng.mapper.UserMapper;
import com.wangpeng.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 定时每天8点开始签到
 */

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {



    //3.添加定时任务
    @Scheduled(cron = "0 06 21 ? * *")
    //或直接指定时间间隔，例如：0 50 20 ? * * 20:50执行
    private void configureTasks() {

    }
}