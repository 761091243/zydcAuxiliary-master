package com.wangpeng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dengwangpeng
 * @dete 2020/11/11 - 20:52
 */
@Data
@TableName("tb_user")
public class User implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String userPwd;
    private Date createTime;



}
