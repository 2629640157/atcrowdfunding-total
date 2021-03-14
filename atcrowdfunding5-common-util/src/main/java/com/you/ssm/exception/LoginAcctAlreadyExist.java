package com.you.ssm.exception;

/**
 * @author 游斌
 * @create 2020-07-04  14:55
 */
public class LoginAcctAlreadyExist extends RuntimeException {
    static final long serialVersionUID = -703489987745766939L;

    public LoginAcctAlreadyExist() {
        super();
    }

    public LoginAcctAlreadyExist(String message) {
        super(message);
    }

    public LoginAcctAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyExist(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
