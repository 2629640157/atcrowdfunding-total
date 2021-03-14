package com.you.ssm.exception;

/**
 * @author 游斌
 * @create 2020-07-02  18:56
 */
public class AccessForbiddenException extends RuntimeException {
    static final long serialVersionUID = -703489789745766939L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

