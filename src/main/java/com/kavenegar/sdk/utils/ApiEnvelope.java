package com.kavenegar.sdk.utils;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiEnvelope<T> {

    @JsonProperty("return")
    @JsonAlias({"return", "result"})
    private ReturnHeader returnHeader;

    @JsonProperty("entries")
    private T entries;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public ReturnHeader getReturnHeader() {
        return returnHeader;
    }

    public void setReturnHeader(ReturnHeader returnHeader) {
        this.returnHeader = returnHeader;
    }

    public T getEntries() {
        return entries;
    }

    public void setEntries(T entries) {
        this.entries = entries;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnHeader {

        @JsonProperty("status")
        private int status;

        @JsonProperty("message")
        private String message;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
