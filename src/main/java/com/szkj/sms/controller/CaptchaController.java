package com.szkj.sms.controller;

import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author BIN
 */
@Controller
@Api(tags = "验证码接口")
public class CaptchaController {
    /**
     * captcha验证码
     *
     * @param request  当前request请求对象
     * @param response 当前response相应对象
     * @throws Exception error
     */
    @ApiOperation(value = "获取Captcha验证码")
    @ResponseBody
    @GetMapping("/code")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }
}
