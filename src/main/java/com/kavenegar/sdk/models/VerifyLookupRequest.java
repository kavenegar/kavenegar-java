package com.kavenegar.sdk.models;

import com.kavenegar.sdk.enums.VerifyLookupType;

public class VerifyLookupRequest {
    private String receptor;
    private String token;
    private String token2;
    private String token3;
    private String token10;
    private String token20;
    private String template;
    private VerifyLookupType type;
    private String tag;

    public VerifyLookupRequest() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getReceptor() { return receptor; }
    public void setReceptor(String receptor) { this.receptor = receptor; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getToken2() { return token2; }
    public void setToken2(String token2) { this.token2 = token2; }

    public String getToken3() { return token3; }
    public void setToken3(String token3) { this.token3 = token3; }

    public String getToken10() { return token10; }
    public void setToken10(String token10) { this.token10 = token10; }

    public String getToken20() { return token20; }
    public void setToken20(String token20) { this.token20 = token20; }

    public String getTemplate() { return template; }
    public void setTemplate(String template) { this.template = template; }

    public VerifyLookupType getType() { return type; }
    public void setType(VerifyLookupType type) { this.type = type; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public static class Builder {
        private final VerifyLookupRequest req = new VerifyLookupRequest();

        public Builder receptor(String val) { req.setReceptor(val); return this; }
        public Builder token(String val) { req.setToken(val); return this; }
        public Builder token2(String val) { req.setToken2(val); return this; }
        public Builder token3(String val) { req.setToken3(val); return this; }
        public Builder token10(String val) { req.setToken10(val); return this; }
        public Builder token20(String val) { req.setToken20(val); return this; }
        public Builder template(String val) { req.setTemplate(val); return this; }
        public Builder type(VerifyLookupType val) { req.setType(val); return this; }
        public Builder tag(String val) { req.setTag(val); return this; }

        public VerifyLookupRequest build() {
            return req;
        }
    }
}
