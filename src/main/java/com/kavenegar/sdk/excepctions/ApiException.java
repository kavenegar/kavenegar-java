package com.kavenegar.sdk.excepctions;

import com.kavenegar.sdk.enums.MetaCode;
import com.kavenegar.sdk.enums.MetaData;

public class ApiException extends KavenegarApiException {

    public ApiException(String message, int code) {
        super(message, code);
    }

    public ApiException(String message, MetaCode code) {
        super(message, code);
    }

    public MetaData getMetaData() {
        return MetaData.valueOf(getRawCode());
    }
}
