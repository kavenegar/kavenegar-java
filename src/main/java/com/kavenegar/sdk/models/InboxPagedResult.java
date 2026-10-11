package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InboxPagedResult {

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @JsonProperty("entries")
    private List<ReceiveResult> entries;

    public InboxPagedResult() {}

    public InboxPagedResult(List<ReceiveResult> entries) {
        this.entries = entries;
    }

    public InboxPagedResult(Map<String, String> metadata, List<ReceiveResult> entries) {
        this.metadata = metadata;
        this.entries = entries;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public List<ReceiveResult> getEntries() {
        return entries;
    }

    public void setEntries(List<ReceiveResult> entries) {
        this.entries = entries;
    }
}
