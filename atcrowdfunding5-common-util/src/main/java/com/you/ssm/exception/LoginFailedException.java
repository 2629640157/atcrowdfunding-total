package com.you.ssm.exception;

/**
 * @author 游斌
 * @create 2020-07-02  10:26
 */
public class LoginFailedException extends RuntimeException {
    static final long serialVersionUID = -7034897190745745939L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
