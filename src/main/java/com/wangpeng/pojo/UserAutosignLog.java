package com.wangpeng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Dunn
 * @date 2021年04月16日 16:27
 */
@Data
@TableName("t_user_autosign_log")
public class UserAutosignLog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String details;
    private Date signTime;



}
