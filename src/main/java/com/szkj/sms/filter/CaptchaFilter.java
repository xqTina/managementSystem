package com.szkj.sms.filter;


import com.szkj.sms.exception.CaptchaException;
import com.szkj.sms.handler.MyAuthenticationFailureHandler;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: CaptchaFilter
 * @Description:
 * @author: sixiwenwu
 * @date: 2021/8/2 15:36
 */
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    public CaptchaFilter(MyAuthenticationFailureHandler myAuthenticationFailureHandler) {
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断是不是POST提交FORM表单
        if ("/login".equalsIgnoreCase(request.getRequestURI())
                && "post".equalsIgnoreCase(request.getMethod())) {
            // 再判断验证码
            if (!CaptchaUtil.ver((String) request.getParameter("code"), request)) {
                // 清除session中的验证码
                CaptchaUtil.clear(request);
                //进入下一步抛回异常
                myAuthenticationFailureHandler.onAuthenticationFailure(request, response, new CaptchaException("验证码错误"));
                return;
            }
        }
        // 不是则放行
        filterChain.doFilter(request, response);
    }

}
