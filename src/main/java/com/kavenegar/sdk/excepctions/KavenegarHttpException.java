package com.kavenegar.sdk.excepctions;

public class KavenegarHttpException extends KavenegarException {

    private final Integer statusCode;

    public KavenegarHttpException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public KavenegarHttpException(String message, Integer statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public int getCode() {
        return statusCode != null ? statusCode : 0;
    }
}
