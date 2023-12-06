package com.szkj.sms.exception;

public class MyException extends Exception {

    private static final long serialVersionUID = 4079142628407820458L;

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(Throwable cause) {
        super(cause);
    }
}
