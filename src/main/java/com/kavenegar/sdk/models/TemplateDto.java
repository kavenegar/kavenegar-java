package com.kavenegar.sdk.models;

import com.kavenegar.sdk.enums.VerificationType;
import com.kavenegar.sdk.enums.VerificationUsageType;

public class TemplateDto {
    public VerificationUsageType sourceType;
    public VerificationType sendMethod;
    public VerificationType fallbackMethod;
    public String primaryLineNumber;
    public String secondaryLineNumber;
    public Integer switchTtl;
    public String sourceUrl;
    public String sourceName;
    public String name;
    public String textMessage;
    public String voiceMessage;
    public String localId;
    public String apiKey;

    public TemplateDto() {}

    public static Builder builder() {
        return new Builder();
    }

    public VerificationUsageType getSourceType() { return sourceType; }
    public void setSourceType(VerificationUsageType sourceType) { this.sourceType = sourceType; }
    public void setSourceType(String sourceType) {
        if (sourceType != null) {
            try {
                this.sourceType = VerificationUsageType.fromValue(Integer.parseInt(sourceType));
            } catch (NumberFormatException e) {
                this.sourceType = VerificationUsageType.OTHER;
            }
        }
    }

    public VerificationType getSendMethod() { return sendMethod; }
    public void setSendMethod(VerificationType sendMethod) { this.sendMethod = sendMethod; }
    public void setSendMethod(String sendMethod) {
        if (sendMethod != null) {
            try {
                this.sendMethod = VerificationType.fromValue(Integer.parseInt(sendMethod));
            } catch (NumberFormatException e) {
                this.sendMethod = VerificationType.DEFAULT;
            }
        }
    }

    public VerificationType getFallbackMethod() { return fallbackMethod; }
    public void setFallbackMethod(VerificationType fallbackMethod) { this.fallbackMethod = fallbackMethod; }
    public void setFallbackMethod(String fallbackMethod) {
        if (fallbackMethod != null) {
            try {
                this.fallbackMethod = VerificationType.fromValue(Integer.parseInt(fallbackMethod));
            } catch (NumberFormatException e) {
                this.fallbackMethod = VerificationType.DEFAULT;
            }
        }
    }

    public String getPrimaryLineNumber() { return primaryLineNumber; }
    public void setPrimaryLineNumber(String primaryLineNumber) { this.primaryLineNumber = primaryLineNumber; }

    public String getSecondaryLineNumber() { return secondaryLineNumber; }
    public void setSecondaryLineNumber(String secondaryLineNumber) { this.secondaryLineNumber = secondaryLineNumber; }

    public Integer getSwitchTtl() { return switchTtl; }
    public void setSwitchTtl(Integer switchTtl) { this.switchTtl = switchTtl; }

    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTemplateName() { return name; }
    public void setTemplateName(String templateName) { this.name = templateName; }

    public String getTextMessage() { return textMessage; }
    public void setTextMessage(String textMessage) { this.textMessage = textMessage; }

    public String getVoiceMessage() { return voiceMessage; }
    public void setVoiceMessage(String voiceMessage) { this.voiceMessage = voiceMessage; }

    public String getLocalId() { return localId; }
    public void setLocalId(String localId) { this.localId = localId; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public static class Builder {
        private final TemplateDto dto = new TemplateDto();

        public Builder sourceType(VerificationUsageType val) { dto.setSourceType(val); return this; }
        public Builder sendMethod(VerificationType val) { dto.setSendMethod(val); return this; }
        public Builder fallbackMethod(VerificationType val) { dto.setFallbackMethod(val); return this; }
        public Builder primaryLineNumber(String val) { dto.setPrimaryLineNumber(val); return this; }
        public Builder secondaryLineNumber(String val) { dto.setSecondaryLineNumber(val); return this; }
        public Builder switchTtl(Integer val) { dto.setSwitchTtl(val); return this; }
        public Builder sourceUrl(String val) { dto.setSourceUrl(val); return this; }
        public Builder sourceName(String val) { dto.setSourceName(val); return this; }
        public Builder name(String val) { dto.setName(val); return this; }
        public Builder templateName(String val) { dto.setTemplateName(val); return this; }
        public Builder textMessage(String val) { dto.setTextMessage(val); return this; }
        public Builder voiceMessage(String val) { dto.setVoiceMessage(val); return this; }
        public Builder localId(String val) { dto.setLocalId(val); return this; }
        public Builder apiKey(String val) { dto.setApiKey(val); return this; }

        public TemplateDto build() {
            return dto;
        }
    }
}
