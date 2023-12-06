package com.szkj.sms.handler;

import com.alibaba.fastjson.JSON;
import com.szkj.sms.entity.SmsLoginHistory;
import com.szkj.sms.service.impl.SmsLoginHistoryServiceImpl;
import com.szkj.sms.util.BrowserUtil;
import com.szkj.sms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @ClassName: MyAuthenticationFailureHandler
 * @Description:
 * @author: sixiwenwu
 * @date: 2021/9/18 14:21
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final SmsLoginHistoryServiceImpl smsLoginHistoryService;

    @Autowired
    private MyAuthenticationFailureHandler(SmsLoginHistoryServiceImpl smsLoginHistoryService) {
        this.smsLoginHistoryService = smsLoginHistoryService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        // 向数据库写入登录记录
        SmsLoginHistory smsLoginHistory = new SmsLoginHistory();
        smsLoginHistory.setLoginUsername(String.valueOf(request.getParameter("username")));
        InetAddress addr;
        addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        smsLoginHistory.setLoginIp(ip);
        smsLoginHistory.setLoginOs(BrowserUtil.osName(request));
        smsLoginHistory.setLoginBrowser(BrowserUtil.browserName(request));
        smsLoginHistory.setLoginResult(e.getMessage());
        smsLoginHistoryService.saveOrUpdate(smsLoginHistory);

        response.setCharacterEncoding("UTF-8");
        // 获取异常信息
        response.getWriter().write(JSON.toJSONString(new JsonResult(-1, "error", e.getMessage())));
    }
}
