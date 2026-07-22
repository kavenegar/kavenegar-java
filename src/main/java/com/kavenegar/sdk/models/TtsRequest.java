package com.kavenegar.sdk.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TtsRequest {
    private String message;
    private List<String> receptors = new ArrayList<>();
    private Instant date;
    private List<String> localIds = new ArrayList<>();
    private String tag;

    public TtsRequest() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<String> getReceptors() { return receptors; }
    public void setReceptors(List<String> receptors) { this.receptors = receptors; }

    public Instant getDate() { return date; }
    public void setDate(Instant date) { this.date = date; }

    public List<String> getLocalIds() { return localIds; }
    public void setLocalIds(List<String> localIds) { this.localIds = localIds; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public static class Builder {
        private final TtsRequest req = new TtsRequest();

        public Builder message(String message) { req.setMessage(message); return this; }
        public Builder receptors(List<String> receptors) { req.setReceptors(receptors); return this; }
        public Builder receptor(String receptor) { req.getReceptors().add(receptor); return this; }
        public Builder date(Instant date) { req.setDate(date); return this; }
        public Builder localIds(List<String> localIds) { req.setLocalIds(localIds); return this; }
        public Builder tag(String tag) { req.setTag(tag); return this; }

        public TtsRequest build() {
            return req;
        }
    }
}
