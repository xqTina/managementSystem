package com.szkj.sms.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户不可用认证异常
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/26 13:12
 */
public class UserUnavailableException extends AuthenticationException {
    public UserUnavailableException(String msg) {
        super(msg);
    }
}
