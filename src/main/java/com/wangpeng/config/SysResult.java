package com.wangpeng.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dengwangpeng
 * @dete 2020/11/11 - 23:44
 */
@Data
@AllArgsConstructor
public class SysResult implements Serializable {
    private Integer code;
    private String text;
    private Object data;
}
