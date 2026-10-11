package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateResult {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("smsmessage")
    private String smsMessage;

    @JsonProperty("callmessage")
    private String callMessage;

    @JsonProperty("primarylinenumber")
    private String primaryLineNumber;

    @JsonProperty("secondarylinenumber")
    private String secondaryLineNumber;

    @JsonProperty("sendpriority")
    private String sendPriority;

    @JsonProperty("approvalstatus")
    private String approvalStatus;

    @JsonProperty("switchttl")
    private int switchTtl;

    public TemplateResult() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    public String getCallMessage() {
        return callMessage;
    }

    public void setCallMessage(String callMessage) {
        this.callMessage = callMessage;
    }

    public String getPrimaryLineNumber() {
        return primaryLineNumber;
    }

    public void setPrimaryLineNumber(String primaryLineNumber) {
        this.primaryLineNumber = primaryLineNumber;
    }

    public String getSecondaryLineNumber() {
        return secondaryLineNumber;
    }

    public void setSecondaryLineNumber(String secondaryLineNumber) {
        this.secondaryLineNumber = secondaryLineNumber;
    }

    public String getSendPriority() {
        return sendPriority;
    }

    public void setSendPriority(String sendPriority) {
        this.sendPriority = sendPriority;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getSwitchTtl() {
        return switchTtl;
    }

    public void setSwitchTtl(int switchTtl) {
        this.switchTtl = switchTtl;
    }
}
