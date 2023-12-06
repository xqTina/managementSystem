package com.szkj.sms.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * @ClassName: UserAlreadyLoginException
 * @Description:
 * @author: sixiwenwu
 * @date: 2021/8/2 14:37
 */
public class UserAlreadyLoginException extends AuthenticationServiceException {
    public UserAlreadyLoginException(String msg) {
        super(msg);
    }
}
