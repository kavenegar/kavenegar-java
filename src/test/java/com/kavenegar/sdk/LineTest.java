package com.kavenegar.sdk;

import com.kavenegar.sdk.models.BlacklistResult;
import com.kavenegar.sdk.models.LineBlockListResult;
import com.kavenegar.sdk.models.RemoveBlacklistResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class LineTest {

    private String apiKey;
    private String receptor;
    private String sender;
    private KavenegarClient api;

    @BeforeEach
    public void setUp() {
        apiKey = System.getenv().getOrDefault("KAVENEGAR_API_KEY", "<put your apikey here>");
        receptor = System.getenv().getOrDefault("KAVENEGAR_RECEPTOR", "<put your receptor here>");
        sender = System.getenv().getOrDefault("KAVENEGAR_SENDER", "<put your sender here>");
        api = new KavenegarClient(apiKey);
    }

    @Test
    public void test_BlockedLineExists() {
        List<BlacklistResult> check = api.blockedLineExists(sender, receptor);
        assertNotNull(check);
    }

    @Test
    public void test_BlockedLineExists_Async() {
        List<BlacklistResult> check = api.blockedLineExistsAsync(sender, receptor).join();
        assertNotNull(check);
    }

    @Test
    public void test_AddBlockedLine() {
        List<BlacklistResult> addedBlock = api.addBlockedLine(receptor, sender);
        assertNotNull(addedBlock);
        api.removeBlockedLine(receptor, sender);
    }

    @Test
    public void test_AddBlockedLine_Async() {
        List<BlacklistResult> addedBlock = api.addBlockedLineAsync(receptor, sender).join();
        assertNotNull(addedBlock);
        api.removeBlockedLineAsync(receptor, sender).join();
    }

    @Test
    public void test_RemoveBlockedLine() {
        api.addBlockedLine(receptor, sender);
        RemoveBlacklistResult removedBlock = api.removeBlockedLine(receptor, sender);
        assertNotNull(removedBlock);
    }

    @Test
    public void test_RemoveBlockedLine_Async() {
        api.addBlockedLineAsync(receptor, sender).join();
        RemoveBlacklistResult removedBlock = api.removeBlockedLineAsync(receptor, sender).join();
        assertNotNull(removedBlock);
    }

    @Test
    public void test_ListBlockedLines() {
        long oneDayAgo = (System.currentTimeMillis() - 86400000L) / 1000L;
        LineBlockListResult blockedList = api.listBlockedLines(sender, 1, oneDayAgo, null);
        assertNotNull(blockedList);
    }

    @Test
    public void test_ListBlockedLines_Async() {
        long oneDayAgo = (System.currentTimeMillis() - 86400000L) / 1000L;
        LineBlockListResult blockedList = api.listBlockedLinesAsync(sender, 1, oneDayAgo, null).join();
        assertNotNull(blockedList);
    }
}
