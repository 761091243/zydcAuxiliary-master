package com.wangpeng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Dunn
 * @date 2021年04月16日 14:16
 */
@Data
@TableName("t_user_autosign")
public class UserAutosign implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String invitationCode;
    private String switchType;
    private Date expireTime;


}
