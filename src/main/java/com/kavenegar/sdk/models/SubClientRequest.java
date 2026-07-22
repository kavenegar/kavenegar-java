package com.kavenegar.sdk.models;

public class SubClientRequest {
    private String apiKey;
    private String ip;
    private String localId;
    private Long credit;
    private String userName;
    private String password;
    private String fullName;
    private String planId;
    private String mobile;
    private Integer status;
    private Integer minimumAllowedCredit;
    private String lines;
    private Boolean allowParentLine;
    private Long expireDate;

    public SubClientRequest() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getLocalId() { return localId; }
    public void setLocalId(String localId) { this.localId = localId; }

    public Long getCredit() { return credit; }
    public void setCredit(Long credit) { this.credit = credit; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getMinimumAllowedCredit() { return minimumAllowedCredit; }
    public void setMinimumAllowedCredit(Integer minimumAllowedCredit) { this.minimumAllowedCredit = minimumAllowedCredit; }

    public String getLines() { return lines; }
    public void setLines(String lines) { this.lines = lines; }

    public Boolean getAllowParentLine() { return allowParentLine; }
    public void setAllowParentLine(Boolean allowParentLine) { this.allowParentLine = allowParentLine; }

    public Long getExpireDate() { return expireDate; }
    public void setExpireDate(Long expireDate) { this.expireDate = expireDate; }

    public static class Builder {
        private final SubClientRequest req = new SubClientRequest();

        public Builder apiKey(String apiKey) { req.setApiKey(apiKey); return this; }
        public Builder ip(String ip) { req.setIp(ip); return this; }
        public Builder localId(String localId) { req.setLocalId(localId); return this; }
        public Builder credit(Long credit) { req.setCredit(credit); return this; }
        public Builder userName(String userName) { req.setUserName(userName); return this; }
        public Builder password(String password) { req.setPassword(password); return this; }
        public Builder fullName(String fullName) { req.setFullName(fullName); return this; }
        public Builder planId(String planId) { req.setPlanId(planId); return this; }
        public Builder mobile(String mobile) { req.setMobile(mobile); return this; }
        public Builder status(Integer status) { req.setStatus(status); return this; }
        public Builder minimumAllowedCredit(Integer val) { req.setMinimumAllowedCredit(val); return this; }
        public Builder lines(String lines) { req.setLines(lines); return this; }
        public Builder allowParentLine(Boolean val) { req.setAllowParentLine(val); return this; }
        public Builder expireDate(Long date) { req.setExpireDate(date); return this; }

        public SubClientRequest build() {
            return req;
        }
    }
}
