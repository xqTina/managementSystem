package com.szkj.sms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 异常控制类
 *
 * @author sixiwenwu
 */
@ControllerAdvice
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * 全局异常返回
     *
     * @param model model存放报错消息
     * @param e     Exception
     * @return 500页
     */
    @ExceptionHandler(Exception.class)
    public String globalException(Model model, Exception e) {
        logger.error(e.getLocalizedMessage());
//        e.printStackTrace();
        // 将错误信息传递回前端
        model.addAttribute("message", e.getMessage());
        return "error/500";
    }

}