package com.kavenegar.sdk.excepctions;

import com.kavenegar.sdk.enums.MetaCode;

public class KavenegarApiException extends KavenegarException {

    private final MetaCode code;
    private final int rawCode;

    public KavenegarApiException(String message, int code) {
        super(message);
        this.rawCode = code;
        this.code = MetaCode.fromValue(code);
    }

    public KavenegarApiException(String message, MetaCode code) {
        super(message);
        this.code = code;
        this.rawCode = code != null ? code.getValue() : -1;
    }

    public MetaCode getCode() {
        return code;
    }

    public int getRawCode() {
        return rawCode;
    }
}
