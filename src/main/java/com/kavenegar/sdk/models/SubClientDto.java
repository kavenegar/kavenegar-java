package com.kavenegar.sdk.models;

public class SubClientDto {
    private String apiKey;
    private String ip;
    private String localId;
    private Double credit;
    private String userName;
    private String password;
    private String fullName;
    private Integer planId;
    private String mobile;
    private String status;
    private Integer mininumAllowedCredit;
    private String lines;
    private Boolean canUseParentLines;
    private String expireDate;
    private Boolean enableLink;
    private Boolean canCharge;

    public SubClientDto() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getLocalId() { return localId; }
    public void setLocalId(String localId) { this.localId = localId; }

    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Integer getPlanId() { return planId; }
    public void setPlanId(Integer planId) { this.planId = planId; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getMininumAllowedCredit() { return mininumAllowedCredit; }
    public void setMininumAllowedCredit(Integer mininumAllowedCredit) { this.mininumAllowedCredit = mininumAllowedCredit; }

    public Integer getMinimumAllowedCredit() { return mininumAllowedCredit; }
    public void setMinimumAllowedCredit(Integer minimumAllowedCredit) { this.mininumAllowedCredit = minimumAllowedCredit; }

    public String getLines() { return lines; }
    public void setLines(String lines) { this.lines = lines; }

    public Boolean getCanUseParentLines() { return canUseParentLines; }
    public void setCanUseParentLines(Boolean canUseParentLines) { this.canUseParentLines = canUseParentLines; }

    public String getExpireDate() { return expireDate; }
    public void setExpireDate(String expireDate) { this.expireDate = expireDate; }

    public Boolean getEnableLink() { return enableLink; }
    public void setEnableLink(Boolean enableLink) { this.enableLink = enableLink; }

    public Boolean getCanCharge() { return canCharge; }
    public void setCanCharge(Boolean canCharge) { this.canCharge = canCharge; }

    public static class Builder {
        private final SubClientDto dto = new SubClientDto();

        public Builder apiKey(String apiKey) { dto.setApiKey(apiKey); return this; }
        public Builder ip(String ip) { dto.setIp(ip); return this; }
        public Builder localId(String localId) { dto.setLocalId(localId); return this; }
        public Builder credit(Double credit) { dto.setCredit(credit); return this; }
        public Builder credit(long credit) { dto.setCredit((double) credit); return this; }
        public Builder userName(String userName) { dto.setUserName(userName); return this; }
        public Builder password(String password) { dto.setPassword(password); return this; }
        public Builder fullName(String fullName) { dto.setFullName(fullName); return this; }
        public Builder planId(Integer planId) { dto.setPlanId(planId); return this; }
        public Builder mobile(String mobile) { dto.setMobile(mobile); return this; }
        public Builder status(String status) { dto.setStatus(status); return this; }
        public Builder mininumAllowedCredit(Integer val) { dto.setMininumAllowedCredit(val); return this; }
        public Builder minimumAllowedCredit(Integer val) { dto.setMininumAllowedCredit(val); return this; }
        public Builder lines(String lines) { dto.setLines(lines); return this; }
        public Builder canUseParentLines(Boolean val) { dto.setCanUseParentLines(val); return this; }
        public Builder expireDate(String date) { dto.setExpireDate(date); return this; }
        public Builder enableLink(Boolean val) { dto.setEnableLink(val); return this; }
        public Builder canCharge(Boolean val) { dto.setCanCharge(val); return this; }

        public SubClientDto build() {
            return dto;
        }
    }
}
