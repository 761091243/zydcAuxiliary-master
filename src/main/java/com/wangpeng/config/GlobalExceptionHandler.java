package com.wangpeng.config;

import com.baomidou.mybatisplus.extension.api.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * 〈全局异常处理类〉<br>
 * @author Administrator
 * @create 2020/8/6
 * @since 1.0.0
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger("异常--------");

    /**
     * @Description 处理所有不可知的异常（controller层）
     * @date 2018年9月10日上午10:38:49
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    SysResult handleException(Exception e){
        LOGGER.error(e.getMessage(), e);
        return new SysResult(200,"接口异常请再次点击",true);
    }




}