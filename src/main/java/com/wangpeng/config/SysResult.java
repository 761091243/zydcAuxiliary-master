package com.wangpeng.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dengwangpeng
 * @dete 2020/11/11 - 23:44
 */
@Data
@AllArgsConstructor
public class SysResult {
    private Integer code;
    private String text;
    private Object date;
}
