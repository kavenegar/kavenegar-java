package com.kavenegar.sdk;

public class KavenegarOptions {

    private String apiKey;
    private String baseUrl = "https://api.kavenegar.com";

    public KavenegarOptions() {
    }

    public KavenegarOptions(String apiKey) {
        this.apiKey = apiKey;
    }

    public KavenegarOptions(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        if (baseUrl != null && !baseUrl.trim().isEmpty()) {
            this.baseUrl = baseUrl;
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public KavenegarClientOptions toClientOptions() {
        return KavenegarClientOptions.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();
    }
}
