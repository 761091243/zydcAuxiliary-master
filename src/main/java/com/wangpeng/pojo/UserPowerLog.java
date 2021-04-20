package com.wangpeng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user_power_log")
public class UserPowerLog {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String userNick;
    private String details;
    private Date powerTime;
}
