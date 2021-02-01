package com.wangpeng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dengwangpeng
 * @dete 2021/2/1 - 21:09
 */
@Data
@TableName("t_user_power")
public class UserPower implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String user_nick;


}
