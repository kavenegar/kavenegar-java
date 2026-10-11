package com.kavenegar.sdk;

import com.kavenegar.sdk.models.SendResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CallTest {

    private String apiKey;
    private String receptor;
    private KavenegarClient api;

    @BeforeEach
    public void setUp() {
        apiKey = System.getenv().getOrDefault("KAVENEGAR_API_KEY", "<put your apikey here>");
        receptor = System.getenv().getOrDefault("KAVENEGAR_RECEPTOR", "<put your receptor here>");
        api = new KavenegarClient(apiKey);
    }

    @Test
    public void test_CallMakeTTS_Overloads() {
        SendResult res1 = api.makeTtsCall("Test voice call 1", receptor);
        assertNotNull(res1);
        List<SendResult> res2 = api.makeTtsCall("Test voice call 2", List.of(receptor));
        assertNotNull(res2);
    }

    @Test
    public void test_CallMakeTTS_Overloads_Async() {
        SendResult res1 = api.makeTtsCallAsync("Test voice call 1", receptor).join();
        assertNotNull(res1);
        List<SendResult> res2 = api.makeTtsCallAsync("Test voice call 2", List.of(receptor)).join();
        assertNotNull(res2);
    }

    @Test
    public void test_CallMakeTTS_WithTag() {
        List<SendResult> res = api.makeTtsCall("Test text-to-speech call", List.of(receptor), null, null, "tts-tag");
        assertNotNull(res);
    }

    @Test
    public void test_CallMakeTTS_WithTag_Async() {
        List<SendResult> res = api.makeTtsCallAsync("Test text-to-speech call", List.of(receptor), null, null, "tts-tag").join();
        assertNotNull(res);
    }
}
