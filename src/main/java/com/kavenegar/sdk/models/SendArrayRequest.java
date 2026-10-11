package com.kavenegar.sdk.models;

import com.kavenegar.sdk.enums.MessageType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SendArrayRequest {
    private List<String> senders = new ArrayList<>();
    private List<String> receptors = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    private List<MessageType> types = new ArrayList<>();
    private Instant date;
    private List<String> localIds = new ArrayList<>();
    private String tag;
    private Integer hide;
    private String hideRaw;
    private String policy;
    private String mediaId;

    public SendArrayRequest() {}

    public static Builder builder() {
        return new Builder();
    }

    public List<String> getSenders() { return senders; }
    public void setSenders(List<String> senders) { this.senders = senders; }

    public List<String> getReceptors() { return receptors; }
    public void setReceptors(List<String> receptors) { this.receptors = receptors; }

    public List<String> getMessages() { return messages; }
    public void setMessages(List<String> messages) { this.messages = messages; }

    public List<MessageType> getTypes() { return types; }
    public void setTypes(List<MessageType> types) { this.types = types; }

    public Instant getDate() { return date; }
    public void setDate(Instant date) { this.date = date; }

    public List<String> getLocalIds() { return localIds; }
    public void setLocalIds(List<String> localIds) { this.localIds = localIds; }

    public List<String> getLocalMessageIds() { return localIds; }
    public void setLocalMessageIds(List<String> localMessageIds) { this.localIds = localMessageIds; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public Integer getHide() { return hide; }
    public void setHide(Integer hide) {
        this.hide = hide;
        this.hideRaw = hide != null ? String.valueOf(hide) : null;
    }
    public void setHide(String hide) {
        this.hideRaw = hide;
        try {
            this.hide = hide != null ? Integer.valueOf(hide) : null;
        } catch (NumberFormatException ignored) {
            this.hide = null;
        }
    }
    public String getHideAsString() {
        if (hideRaw != null) return hideRaw;
        return hide != null ? String.valueOf(hide) : null;
    }

    public String getPolicy() { return policy; }
    public void setPolicy(String policy) { this.policy = policy; }

    public String getMediaId() { return mediaId; }
    public void setMediaId(String mediaId) { this.mediaId = mediaId; }

    public static class Builder {
        private final SendArrayRequest request = new SendArrayRequest();

        public Builder senders(List<String> senders) { request.setSenders(senders); return this; }
        public Builder receptors(List<String> receptors) { request.setReceptors(receptors); return this; }
        public Builder messages(List<String> messages) { request.setMessages(messages); return this; }
        public Builder types(List<MessageType> types) { request.setTypes(types); return this; }
        public Builder date(Instant date) { request.setDate(date); return this; }
        public Builder localIds(List<String> localIds) { request.setLocalIds(localIds); return this; }
        public Builder localMessageIds(List<String> localMessageIds) { request.setLocalMessageIds(localMessageIds); return this; }
        public Builder tag(String tag) { request.setTag(tag); return this; }
        public Builder hide(Integer hide) { request.setHide(hide); return this; }
        public Builder hide(String hide) { request.setHide(hide); return this; }
        public Builder policy(String policy) { request.setPolicy(policy); return this; }
        public Builder mediaId(String mediaId) { request.setMediaId(mediaId); return this; }

        public SendArrayRequest build() {
            return request;
        }
    }
}
