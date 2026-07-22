package com.kavenegar.sdk.excepctions;

public class HttpException extends KavenegarHttpException {

    public HttpException(String message, int statusCode) {
        super(message, statusCode);
    }
}
