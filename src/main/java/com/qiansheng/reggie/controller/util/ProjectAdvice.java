package com.qiansheng.reggie.controller.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * 拦截500的异常
 */
@RestControllerAdvice(annotations = {RestController.class})
@Slf4j
public class ProjectAdvice implements Serializable {
    /**
     * 拦截数据库的异常
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public R sqlE(SQLException exception){
        log.info(exception.getMessage());
        return R.error("数据异常！");
    }

    /**
     * 拦截空指针异常
     * @param exception
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public R nullE(NullPointerException exception){
        log.info(exception.getMessage());
        return R.error("空指针异常！");
    }
}
