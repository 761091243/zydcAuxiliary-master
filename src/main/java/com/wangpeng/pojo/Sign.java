package com.wangpeng.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dengwangpeng
 * @dete 2020/11/11 - 21:18
 */
@Data
@TableName("tb_sign")
public class Sign implements Serializable{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private Date createTime;
}
