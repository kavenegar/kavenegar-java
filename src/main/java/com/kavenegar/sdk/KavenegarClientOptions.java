package com.kavenegar.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.time.Duration;

public class KavenegarClientOptions {

    private final String apiKey;
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Duration timeout;
    private final ObjectMapper objectMapper;

    private KavenegarClientOptions(Builder builder) {
        this.apiKey = builder.apiKey;
        this.baseUrl = builder.baseUrl;
        this.httpClient = builder.httpClient != null ? builder.httpClient : HttpClient.newBuilder().connectTimeout(builder.timeout).build();
        this.timeout = builder.timeout;
        this.objectMapper = builder.objectMapper != null ? builder.objectMapper : new ObjectMapper();
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String apiKey;
        private String baseUrl = "https://api.kavenegar.com";
        private HttpClient httpClient;
        private Duration timeout = Duration.ofSeconds(30);
        private ObjectMapper objectMapper;

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder objectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        public KavenegarClientOptions build() {
            return new KavenegarClientOptions(this);
        }
    }
}
