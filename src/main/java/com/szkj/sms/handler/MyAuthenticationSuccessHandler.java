package com.szkj.sms.handler;

import com.alibaba.fastjson.JSON;
import com.szkj.sms.entity.SmsLoginHistory;
import com.szkj.sms.service.impl.SmsLoginHistoryServiceImpl;
import com.szkj.sms.util.BrowserUtil;
import com.szkj.sms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @ClassName: MyAuthenticationSuccessHandler
 * @Description:
 * @author: sixiwenwu
 * @date: 2021/8/2 14:18
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String DEV = "[ROLE_DEV]";
    private final SmsLoginHistoryServiceImpl smsLoginHistoryService;

    @Autowired
    private MyAuthenticationSuccessHandler(SmsLoginHistoryServiceImpl smsLoginHistoryService) {
        this.smsLoginHistoryService = smsLoginHistoryService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // 非开发人员的登录日志
        if (!DEV.equals(String.valueOf(authentication.getAuthorities()))) {
            // 向数据库写入登录记录
            SmsLoginHistory smsLoginHistory = new SmsLoginHistory();
            smsLoginHistory.setLoginUsername(authentication.getName());
            smsLoginHistory.setLoginUserRole(String.valueOf(authentication.getAuthorities()));
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            smsLoginHistory.setLoginIp(ip);
            smsLoginHistory.setLoginOs(BrowserUtil.osName(httpServletRequest));
            smsLoginHistory.setLoginBrowser(BrowserUtil.browserName(httpServletRequest));
            smsLoginHistory.setLoginResult("登录成功");
            smsLoginHistoryService.saveOrUpdate(smsLoginHistory);
        }
        httpServletResponse.setCharacterEncoding("UTF-8");
        // 返回登录成功
        httpServletResponse.getWriter().write(JSON.toJSONString(new JsonResult(0, "success", authentication)));
    }
}
