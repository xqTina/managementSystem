package com.szkj.sms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.szkj.sms.dto.DataZhenXianJiNewDto;
import com.szkj.sms.entity.*;
import com.szkj.sms.service.*;
import com.szkj.sms.util.JsonResult;
import com.szkj.sms.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    public Object toLogin(HttpServletRequest request) {

        return "login";
    }


    // 退出时情况session

    @ApiOperation(value = "删除Session中数据")
    @GetMapping("/deleteSession")
    public void deleteSession(HttpServletRequest request) {
        request.getSession().removeAttribute("dtuData");

        System.out.println("=============删除Session中数据=============");
    }
}
