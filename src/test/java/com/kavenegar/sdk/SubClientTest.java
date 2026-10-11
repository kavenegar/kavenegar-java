package com.kavenegar.sdk;

import com.kavenegar.sdk.models.SubClientDto;
import com.kavenegar.sdk.models.SubClientResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

public class SubClientTest {

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
    public void test_ListClients() {
        List<SubClientResult> res = api.listClients();
        assertNotNull(res);
    }

    @Test
    public void test_ListClients_Async() {
        List<SubClientResult> res = api.listClientsAsync().join();
        assertNotNull(res);
    }

    @Test
    public void test_AddClient() {
        String clientName = "Add Test " + UUID.randomUUID().toString().substring(0, 8);
        SubClientDto req = SubClientDto.builder()
                .fullName(clientName)
                .userName("user_" + UUID.randomUUID().toString().substring(0, 8))
                .password("Password123!")
                .mobile(receptor)
                .credit(1000L)
                .build();
        SubClientResult newClient = api.addClient(req);
        assertNotNull(newClient);
        if (newClient.getApiKey() != null) {
            api.setClientStatus(newClient.getApiKey(), 2);
        }
    }

    @Test
    public void test_AddClient_Async() {
        String clientName = "Add Test " + UUID.randomUUID().toString().substring(0, 8);
        SubClientDto req = SubClientDto.builder()
                .fullName(clientName)
                .userName("user_" + UUID.randomUUID().toString().substring(0, 8))
                .password("Password123!")
                .mobile(receptor)
                .credit(1000L)
                .build();
        SubClientResult newClient = api.addClientAsync(req).join();
        assertNotNull(newClient);
        if (newClient.getApiKey() != null) {
            api.setClientStatusAsync(newClient.getApiKey(), 2).join();
        }
    }

    @Test
    public void test_FetchClient() {
        SubClientResult fetched = api.fetchClient(apiKey);
        assertNotNull(fetched);
    }

    @Test
    public void test_FetchClient_Async() {
        SubClientResult fetched = api.fetchClientAsync(apiKey).join();
        assertNotNull(fetched);
    }

    @Test
    public void test_FetchClientByLocalId() {
        SubClientResult res = api.fetchClientByLocalId("local_1");
        assertNotNull(res);
    }

    @Test
    public void test_FetchClientByLocalId_Async() {
        SubClientResult res = api.fetchClientByLocalIdAsync("local_1").join();
        assertNotNull(res);
    }

    @Test
    public void test_RenewClientKey() {
        SubClientResult res = api.renewClientKey(apiKey, null);
        assertNotNull(res);
    }

    @Test
    public void test_RenewClientKey_Async() {
        SubClientResult res = api.renewClientKeyAsync(apiKey, null).join();
        assertNotNull(res);
    }

    @Test
    public void test_UpdateClient() {
        SubClientDto req = SubClientDto.builder()
                .apiKey(apiKey)
                .fullName("Updated SubClient")
                .build();
        SubClientResult res = api.updateClient(req);
        assertNotNull(res);
    }

    @Test
    public void test_UpdateClient_Async() {
        SubClientDto req = SubClientDto.builder()
                .apiKey(apiKey)
                .fullName("Updated SubClient")
                .build();
        SubClientResult res = api.updateClientAsync(req).join();
        assertNotNull(res);
    }

    @Test
    public void test_ChargeClientCredit() {
        SubClientResult res = api.chargeClientCredit(apiKey, 1000, "test charge", null, null);
        assertNotNull(res);
    }

    @Test
    public void test_ChargeClientCredit_Async() {
        SubClientResult res = api.chargeClientCreditAsync(apiKey, 1000, "test charge", null, null).join();
        assertNotNull(res);
    }

    @Test
    public void test_SetClientStatus() {
        SubClientResult res = api.setClientStatus(apiKey, 1);
        assertNotNull(res);
    }

    @Test
    public void test_SetClientStatus_Async() {
        SubClientResult res = api.setClientStatusAsync(apiKey, 1).join();
        assertNotNull(res);
    }
}
