package com.kavenegar.sdk;

import com.kavenegar.sdk.enums.MessageType;
import com.kavenegar.sdk.enums.MetaCode;
import com.kavenegar.sdk.models.SendResult;
import com.kavenegar.sdk.utils.UnixTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientUnitTest {

    private KavenegarClient api;
    private final String apiKey = System.getenv("KAVENEGAR_API_KEY") != null ? System.getenv("KAVENEGAR_API_KEY") : "YOUR_API_KEY";
    private final String receptor = System.getenv("KAVENEGAR_RECEPTOR") != null ? System.getenv("KAVENEGAR_RECEPTOR") : "09120000000";
    private final String sender = System.getenv("KAVENEGAR_SENDER") != null ? System.getenv("KAVENEGAR_SENDER") : "10004346";

    @BeforeEach
    public void setUp() {
        api = new KavenegarClient(apiKey);
    }

    @Test
    public void testSendRealCall() {
        List<SendResult> results = api.send(sender, List.of(receptor), "Hello Real Call");
        assertNotNull(results);
    }

    @Test
    public void testSendAsyncRealCall() throws Exception {
        List<SendResult> results = api.sendAsync(sender, List.of(receptor), "Hello Async Real Call").get();
        assertNotNull(results);
    }

    @Test
    public void testEnumMapping() {
        assertEquals(MessageType.FLASH, MessageType.fromValue(0));
        assertEquals(MessageType.MOBILE_MEMORY, MessageType.fromValue(1));

        assertEquals(MetaCode.APPROVED, MetaCode.fromValue(100));
        assertEquals(MetaCode.INVALID_API_KEY, MetaCode.fromValue(101));
        assertEquals(MetaCode.UNKNOWN, MetaCode.fromValue(999));
    }

    @Test
    public void testUnixTimeHelper() {
        Instant now = Instant.ofEpochSecond(1700000000L);
        assertEquals(1700000000L, UnixTime.toEpochSeconds(now));
        assertEquals(now, UnixTime.fromEpochSeconds(1700000000L));

        Date date = new Date(1700000000000L);
        assertEquals(1700000000L, UnixTime.toEpochSeconds(date));
    }
}
