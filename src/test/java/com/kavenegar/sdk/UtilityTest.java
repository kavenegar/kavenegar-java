package com.kavenegar.sdk;

import com.kavenegar.sdk.models.AccountConfigResult;
import com.kavenegar.sdk.models.AccountInfoResult;
import com.kavenegar.sdk.models.ServerDateResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.http.HttpClient;
import java.time.Duration;

public class UtilityTest {

    private String apiKey;
    private KavenegarClient api;

    @BeforeEach
    public void setUp() {
        apiKey = System.getenv().getOrDefault("KAVENEGAR_API_KEY", "<put your apikey here>");
        api = new KavenegarClient(apiKey);
    }

    @Test
    public void test_Utils() {
        String ping = api.ping();
        assertNotNull(ping);

        ServerDateResult date = api.getServerDate();
        assertNotNull(date);
    }

    @Test
    public void test_Utils_Async() {
        String ping = api.pingAsync().join();
        assertNotNull(ping);

        ServerDateResult date = api.getServerDateAsync().join();
        assertNotNull(date);
    }

    @Test
    public void test_Account_Info_And_Config() {
        AccountInfoResult info = api.getAccountInfo();
        assertNotNull(info);

        AccountConfigResult config = api.configureAccount("enabled", "enabled", "enabled", "<put your sender here>", 10000, "enabled");
        assertNotNull(config);
    }

    @Test
    public void test_Account_Info_And_Config_Async() {
        AccountInfoResult info = api.getAccountInfoAsync().join();
        assertNotNull(info);

        AccountConfigResult config = api.configureAccountAsync("enabled", "enabled", "enabled", "<put your sender here>", 10000, "enabled").join();
        assertNotNull(config);
    }

    @Test
    public void test_HttpClient_Constructor_Injection() {
        HttpClient customClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();

        KavenegarClientOptions options = KavenegarClientOptions.builder()
                .apiKey("MOCK_API_KEY")
                .httpClient(customClient)
                .build();

        KavenegarClient customApi = new KavenegarClient(options);
        assertNotNull(customApi);
        assertEquals("MOCK_API_KEY", customApi.getApiKey());
    }

    @Test
    public void test_HttpClient_Constructor_Injection_Async() {
        HttpClient customClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();

        KavenegarClientOptions options = KavenegarClientOptions.builder()
                .apiKey("MOCK_API_KEY")
                .httpClient(customClient)
                .build();

        KavenegarClient customApi = new KavenegarClient(options);
        assertNotNull(customApi);
        assertEquals("MOCK_API_KEY", customApi.getApiKey());
    }
}
