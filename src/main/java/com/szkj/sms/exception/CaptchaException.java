package com.szkj.sms.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @ClassName: CaptchaException
 * @Description:
 * @author: sixiwenwu
 * @date: 2021/8/2 15:33
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }
}
