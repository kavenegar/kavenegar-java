package com.kavenegar.sdk;

import com.kavenegar.sdk.enums.*;
import com.kavenegar.sdk.models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyTest {

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
    public void test_Verify_Lookup_Overloads() {
        SendResult res1 = api.verifyLookup(receptor, "123456", "otp-template");
        assertNotNull(res1);
        SendResult res2 = api.verifyLookup(receptor, "123456", "otp-template", VerifyLookupType.SMS);
        assertNotNull(res2);

        SendResult res2Token1 = api.verifyLookup(receptor, "123", "456", "otp-template-1");
        assertNotNull(res2Token1);
        SendResult res2Token2 = api.verifyLookup(receptor, "123", "456", "otp-template-1", VerifyLookupType.SMS);
        assertNotNull(res2Token2);

        SendResult res3 = api.verifyLookup(receptor, "123", "456", "789", "otp-template-2");
        assertNotNull(res3);
        SendResult res4 = api.verifyLookup(receptor, "123", "456", "789", "otp-template-2", VerifyLookupType.SMS);
        assertNotNull(res4);

        SendResult res5 = api.verifyLookup(receptor, "123", "456", "789", "token10", "otp-template-3");
        assertNotNull(res5);
        SendResult res6 = api.verifyLookup(receptor, "123", "456", "789", "token10", "otp-template-3", VerifyLookupType.SMS);
        assertNotNull(res6);

        SendResult res7 = api.verifyLookup(receptor, "123", "456", "789", "token10", "token20", "otp-template-4", VerifyLookupType.SMS, null);
        assertNotNull(res7);
        SendResult res8 = api.verifyLookup(receptor, "123", "456", "789", "token10", "token20", "otp-template-4", VerifyLookupType.SMS, null);
        assertNotNull(res8);
    }

    @Test
    public void test_Verify_Lookup_Overloads_Async() {
        SendResult res1 = api.verifyLookupAsync(receptor, "123456", "otp-template").join();
        assertNotNull(res1);
        SendResult res2 = api.verifyLookupAsync(receptor, "123456", "otp-template", VerifyLookupType.SMS).join();
        assertNotNull(res2);

        SendResult res2Token1 = api.verifyLookupAsync(receptor, "123", "456", "otp-template-1").join();
        assertNotNull(res2Token1);
        SendResult res2Token2 = api.verifyLookupAsync(receptor, "123", "456", "otp-template-1", VerifyLookupType.SMS).join();
        assertNotNull(res2Token2);

        SendResult res3 = api.verifyLookupAsync(receptor, "123", "456", "789", "otp-template-2").join();
        assertNotNull(res3);
        SendResult res4 = api.verifyLookupAsync(receptor, "123", "456", "789", "otp-template-2", VerifyLookupType.SMS).join();
        assertNotNull(res4);

        SendResult res5 = api.verifyLookupAsync(receptor, "123", "456", "789", "token10", "otp-template-3").join();
        assertNotNull(res5);
        SendResult res6 = api.verifyLookupAsync(receptor, "123", "456", "789", "token10", "otp-template-3", VerifyLookupType.SMS).join();
        assertNotNull(res6);

        SendResult res7 = api.verifyLookupAsync(receptor, "123", "456", "789", "token10", "token20", "otp-template-4", VerifyLookupType.SMS, null).join();
        assertNotNull(res7);
        SendResult res8 = api.verifyLookupAsync(receptor, "123", "456", "789", "token10", "token20", "otp-template-4", VerifyLookupType.SMS, null).join();
        assertNotNull(res8);
    }

    @Test
    public void test_VerifyLookup_Extended() {
        SendResult res = api.verifyLookup(receptor, "123456", null, null, null, null, "otp-template", VerifyLookupType.SMS, "test");
        assertNotNull(res);
    }

    @Test
    public void test_VerifyLookup_Extended_Async() {
        SendResult res = api.verifyLookupAsync(receptor, "123456", null, null, null, null, "otp-template", VerifyLookupType.SMS, "test").join();
        assertNotNull(res);
    }

    @Test
    public void test_ListTemplates() {
        var templates = api.listTemplates(null, null, null);
        assertNotNull(templates);
    }

    @Test
    public void test_ListTemplates_Async() {
        var templates = api.listTemplatesAsync(null, null, null).join();
        assertNotNull(templates);
    }

    @Test
    public void test_AddTemplate() {
        TemplateInfoResult added = createTempTemplate(api);
        assertNotNull(added);
        if (added.getTemplateId() != null) {
            api.deleteTemplate(Integer.parseInt(added.getTemplateId()), null, null);
        }
    }

    @Test
    public void test_AddTemplate_Async() {
        TemplateInfoResult added = createTempTemplateAsync(api);
        assertNotNull(added);
        if (added.getTemplateId() != null) {
            api.deleteTemplateAsync(Integer.parseInt(added.getTemplateId()), null, null).join();
        }
    }

    @Test
    public void test_UpdateTemplate() {
        TemplateInfoResult added = createTempTemplate(api);
        TemplateDto dto = new TemplateDto();
        dto.name = "otp-template";
        dto.textMessage = "کد تایید بروزرسانی شد: %token%";
        dto.sourceType = VerificationUsageType.WEB_APPLICATION;
        dto.sendMethod = VerificationType.MESSAGE;

        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            TemplateInfoResult updated = api.updateTemplate(id, dto);
            assertNotNull(updated);
            api.deleteTemplate(id, null, null);
        }
    }

    @Test
    public void test_UpdateTemplate_Async() {
        TemplateInfoResult added = createTempTemplateAsync(api);
        TemplateDto dto = new TemplateDto();
        dto.name = "otp-template";
        dto.textMessage = "کد تایید بروزرسانی شد: %token%";
        dto.sourceType = VerificationUsageType.WEB_APPLICATION;
        dto.sendMethod = VerificationType.MESSAGE;

        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            TemplateInfoResult updated = api.updateTemplateAsync(id, dto).join();
            assertNotNull(updated);
            api.deleteTemplateAsync(id, null, null).join();
        }
    }

    @Test
    public void test_GetTemplate() {
        TemplateInfoResult added = createTempTemplate(api);
        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            TemplateResult detail = api.getTemplate(id, null, null);
            assertNotNull(detail);
            api.deleteTemplate(id, null, null);
        }
    }

    @Test
    public void test_GetTemplate_Async() {
        TemplateInfoResult added = createTempTemplateAsync(api);
        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            TemplateResult detail = api.getTemplateAsync(id, null, null).join();
            assertNotNull(detail);
            api.deleteTemplateAsync(id, null, null).join();
        }
    }

    @Test
    public void test_CloneTemplate() {
        TemplateInfoResult added = createTempTemplate(api);
        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            CloneTemplateResult cloned = api.cloneTemplate(id, null, added.getName() + "-copy", null, null);
            assertNotNull(cloned);
            api.deleteTemplate(id, null, null);
            if (cloned.getId() > 0) {
                api.deleteTemplate(cloned.getId(), null, null);
            }
        }
    }

    @Test
    public void test_CloneTemplate_Async() {
        TemplateInfoResult added = createTempTemplateAsync(api);
        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            CloneTemplateResult cloned = api.cloneTemplateAsync(id, null, added.getName() + "-copy", null, null).join();
            assertNotNull(cloned);
            api.deleteTemplateAsync(id, null, null).join();
            if (cloned.getId() > 0) {
                api.deleteTemplateAsync(cloned.getId(), null, null).join();
            }
        }
    }

    @Test
    public void test_DeleteTemplate() {
        TemplateInfoResult added = createTempTemplate(api);
        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            String deleteResult = api.deleteTemplate(id, null, null);
            assertNotNull(deleteResult);
        }
    }

    @Test
    public void test_DeleteTemplate_Async() {
        TemplateInfoResult added = createTempTemplateAsync(api);
        if (added.getTemplateId() != null) {
            int id = Integer.parseInt(added.getTemplateId());
            String deleteResult = api.deleteTemplateAsync(id, null, null).join();
            assertNotNull(deleteResult);
        }
    }

    private TemplateInfoResult createTempTemplate(KavenegarClient api) {
        TemplateDto dto = new TemplateDto();
        dto.name = "temp" + new Random().nextInt(10000);
        dto.textMessage = "کد تایید: %token%";
        dto.sourceType = VerificationUsageType.WEB_APPLICATION;
        dto.sendMethod = VerificationType.MESSAGE;
        return api.addTemplate(dto);
    }

    private TemplateInfoResult createTempTemplateAsync(KavenegarClient api) {
        TemplateDto dto = new TemplateDto();
        dto.name = "temp" + new Random().nextInt(10000);
        dto.textMessage = "کد تایید: %token%";
        dto.sourceType = VerificationUsageType.WEB_APPLICATION;
        dto.sendMethod = VerificationType.MESSAGE;
        return api.addTemplateAsync(dto).join();
    }
}
