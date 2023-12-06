package com.szkj.sms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sixiwenwu
 */
@Controller
@Api(tags = "登录相关接口")
public class LoginController {

    /**
     * 登录页面
     *
     * @return 页面名称
     */
    @ApiOperation(value = "登录页页面路由")
    @GetMapping("/tologin")
    public String toLogin() {
        return "login";
    }

}
