package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LineBlockListResult {

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @JsonProperty("entries")
    private List<BlockedLineDetail> entries;

    public LineBlockListResult() {}

    public LineBlockListResult(List<BlockedLineDetail> entries) {
        this.entries = entries;
    }

    public LineBlockListResult(Map<String, String> metadata, List<BlockedLineDetail> entries) {
        this.metadata = metadata;
        this.entries = entries;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public List<BlockedLineDetail> getEntries() {
        return entries;
    }

    public void setEntries(List<BlockedLineDetail> entries) {
        this.entries = entries;
    }
}
