package com.kavenegar.sdk.models;

import com.kavenegar.sdk.enums.MessageType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SendRequest {
    private String sender;
    private List<String> receptors = new ArrayList<>();
    private String message;
    private MessageType type = MessageType.MOBILE_MEMORY;
    private Instant date;
    private List<String> localIds = new ArrayList<>();
    private String tag;
    private String text;
    private Integer hide;
    private String localId;
    private String policy;
    private String mediaId;

    public SendRequest() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public List<String> getReceptors() { return receptors; }
    public void setReceptors(List<String> receptors) { this.receptors = receptors; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    public Instant getDate() { return date; }
    public void setDate(Instant date) { this.date = date; }

    public List<String> getLocalIds() { return localIds; }
    public void setLocalIds(List<String> localIds) { this.localIds = localIds; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Integer getHide() { return hide; }
    public void setHide(Integer hide) { this.hide = hide; }

    public String getLocalId() { return localId; }
    public void setLocalId(String localId) { this.localId = localId; }

    public String getPolicy() { return policy; }
    public void setPolicy(String policy) { this.policy = policy; }

    public String getMediaId() { return mediaId; }
    public void setMediaId(String mediaId) { this.mediaId = mediaId; }

    public static class Builder {
        private final SendRequest request = new SendRequest();

        public Builder sender(String sender) { request.setSender(sender); return this; }
        public Builder receptors(List<String> receptors) { request.setReceptors(receptors); return this; }
        public Builder receptor(String receptor) { request.getReceptors().add(receptor); return this; }
        public Builder message(String message) { request.setMessage(message); return this; }
        public Builder type(MessageType type) { request.setType(type); return this; }
        public Builder date(Instant date) { request.setDate(date); return this; }
        public Builder localIds(List<String> localIds) { request.setLocalIds(localIds); return this; }
        public Builder tag(String tag) { request.setTag(tag); return this; }
        public Builder text(String text) { request.setText(text); return this; }
        public Builder hide(Integer hide) { request.setHide(hide); return this; }
        public Builder localId(String localId) { request.setLocalId(localId); return this; }
        public Builder policy(String policy) { request.setPolicy(policy); return this; }
        public Builder mediaId(String mediaId) { request.setMediaId(mediaId); return this; }

        public SendRequest build() {
            return request;
        }
    }
}
