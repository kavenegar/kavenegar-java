package com.kavenegar.sdk.excepctions;

public class KavenegarException extends RuntimeException {

    public KavenegarException(String message) {
        super(message);
    }

    public KavenegarException(String message, Throwable cause) {
        super(message, cause);
    }

    public KavenegarException(Throwable cause) {
        super(cause);
    }
}
