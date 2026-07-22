package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateInfoResult {

    @JsonProperty("name")
    private String name;

    @JsonProperty("sourcetype")
    private String sourceType;

    @JsonProperty("sendmethod")
    private String sendMethod;

    @JsonProperty("fallbackmethod")
    private String fallbackMethod;

    @JsonProperty("primarylinenumber")
    private String primaryLineNumber;

    @JsonProperty("secondarylinenumber")
    private String secondaryLineNumber;

    @JsonProperty("sourceurl")
    private String sourceUrl;

    @JsonProperty("sourcename")
    private String sourceName;

    @JsonProperty("textmessage")
    private String textMessage;

    @JsonProperty("voicemessage")
    private String voiceMessage;

    @JsonProperty("templateid")
    private String templateId;

    @JsonProperty("switchttl")
    private int switchTtl;

    public TemplateInfoResult() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSendMethod() {
        return sendMethod;
    }

    public void setSendMethod(String sendMethod) {
        this.sendMethod = sendMethod;
    }

    public String getFallbackMethod() {
        return fallbackMethod;
    }

    public void setFallbackMethod(String fallbackMethod) {
        this.fallbackMethod = fallbackMethod;
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

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getVoiceMessage() {
        return voiceMessage;
    }

    public void setVoiceMessage(String voiceMessage) {
        this.voiceMessage = voiceMessage;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getSwitchTtl() {
        return switchTtl;
    }

    public void setSwitchTtl(int switchTtl) {
        this.switchTtl = switchTtl;
    }
}
