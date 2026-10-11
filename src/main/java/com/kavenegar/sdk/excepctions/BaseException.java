package com.kavenegar.sdk.excepctions;

public class BaseException extends KavenegarException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
