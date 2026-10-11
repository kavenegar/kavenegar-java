package com.kavenegar.sdk;

import com.kavenegar.sdk.enums.MessageType;
import com.kavenegar.sdk.models.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class SmsTest {

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
    public void test_Sms_Send_Overload_1() {
        List<SendResult> res = api.send(sender, List.of(receptor), "تست پیام ۱");
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_1_Async() {
        List<SendResult> res = api.sendAsync(sender, List.of(receptor), "تست پیام ۱").join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_2() {
        SendResult res = api.send(sender, receptor, "تست پیام ۲");
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_2_Async() {
        SendResult res = api.sendAsync(sender, receptor, "تست پیام ۲").join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_3() {
        SendResult res = api.send(sender, receptor, "تست پیام ۳", MessageType.MOBILE_MEMORY, new Date(0));
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_3_Async() {
        SendResult res = api.sendAsync(sender, receptor, "تست پیام ۳", new Date(0)).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_4() {
        List<SendResult> res = api.send(sender, List.of(receptor), "تست پیام ۴", MessageType.MOBILE_MEMORY, new Date(0));
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_4_Async() {
        List<SendResult> res = api.sendAsync(sender, List.of(receptor), "تست پیام ۴", new Date(0)).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_5() {
        SendResult res = api.send(sender, receptor, "تست پیام ۵", MessageType.MOBILE_MEMORY, new Date(0), "localid123");
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_5_Async() {
        SendResult res = api.sendAsync(sender, receptor, "تست پیام ۵", new Date(0), "localid123").join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_6() {
        SendResult first = api.send(sender, receptor, "تست پیام ۶", "localid4564");
        SendResult seconde = api.send(sender, receptor, "تست پیام ۶", "localid4564");
        assertNotNull(first);
        assertNotNull(seconde);
    }

    @Test
    public void test_Sms_Send_Overload_6_Async() {
        SendResult first = api.sendAsync(sender, receptor, "تست پیام ۶", "localid4564").join();
        SendResult seconde = api.sendAsync(sender, receptor, "تست پیام ۶", "localid4564").join();
        assertNotNull(first);
        assertNotNull(seconde);
    }

    @Test
    public void test_Sms_Send_Overload_7() {
        List<SendResult> res = api.send(sender, List.of(receptor), "تست پیام ۷", "localid789");
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Overload_7_Async() {
        List<SendResult> res = api.sendAsync(sender, List.of(receptor), "تست پیام ۷", "localid789").join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Extend() {
        List<SendResult> res = api.send(sender, List.of(receptor), "تست پیام پیشرفته", MessageType.MOBILE_MEMORY, new Date(0), null, null, null, null, null, null, null);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Extend_Async() {
        List<SendResult> res = api.sendAsync(sender, List.of(receptor), "تست پیام پیشرفته", new Date(0), null, null, null, null, null, null, null).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Extended() {
        SendRequest req = SendRequest.builder()
                .sender(sender)
                .receptors(List.of(receptor))
                .message("تست ارسال پیشرفته با خط‌مشی")
                .type(MessageType.MOBILE_MEMORY)
                .localId("local-id-test-12")
                .policy("mix")
                .build();
        List<SendResult> res = api.send(req);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Send_Extended_Async() {
        SendRequest req = SendRequest.builder()
                .sender(sender)
                .receptors(List.of(receptor))
                .message("تست ارسال پیشرفته با خط‌مشی")
                .type(MessageType.MOBILE_MEMORY)
                .localId("local-id-test-12")
                .policy("mix")
                .build();
        List<SendResult> res = api.sendAsync(req).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_1() {
        List<SendResult> res = api.sendArray(List.of(sender), List.of(receptor), List.of("پیام آرایه ۱"));
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_1_Async() {
        List<SendResult> res = api.sendArrayAsync(List.of(sender), List.of(receptor), List.of("پیام آرایه ۱")).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_2() {
        List<SendResult> res = api.sendArray(sender, List.of(receptor), List.of("پیام آرایه ۲"), MessageType.MOBILE_MEMORY, new Date(0));
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_2_Async() {
        List<SendResult> res = api.sendArrayAsync(sender, List.of(receptor), List.of("پیام آرایه ۲"), new Date(0)).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_3() {
        List<SendResult> res = api.sendArray(sender, List.of(receptor), List.of("پیام آرایه ۳"), MessageType.MOBILE_MEMORY, new Date(0), "localmsgid123");
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_3_Async() {
        List<SendResult> res = api.sendArrayAsync(sender, List.of(receptor), List.of("پیام آرایه ۳"), new Date(0), "localmsgid123").join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_4() {
        List<SendResult> res = api.sendArray(sender, List.of(receptor), List.of("پیام آرایه ۴"), "localmsgid456");
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_4_Async() {
        List<SendResult> res = api.sendArrayAsync(sender, List.of(receptor,receptor), List.of("پیام آرایه ۴","پیام آرایه "), "localmsgid456").join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_5() {
        List<SendResult> res = api.sendArray(List.of(sender), List.of(receptor), List.of("پیام آرایه ۵"), "localmsgid789");
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Overload_5_Async() {
        List<SendResult> res = api.sendArrayAsync(List.of(sender), List.of(receptor), List.of("پیام آرایه ۵"), "localmsgid789").join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_11Params() {
        List<SendResult> res = api.sendArray(List.of(sender), List.of(receptor), List.of("پیام آرایه قدیمی"), null, null, null, null, null, null, null);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_11Params_Async() {
        List<SendResult> res = api.sendArrayAsync(List.of(sender), List.of(receptor), List.of("پیام آرایه قدیمی"), null, null, null, null, null, null).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Extended() {
        SendArrayRequest req = SendArrayRequest.builder()
                .senders(List.of(sender))
                .receptors(List.of(receptor))
                .messages(List.of("تست ارسال پیشرفته با خط‌مشی"))
                .types(List.of(MessageType.MOBILE_MEMORY))
                .date(new Date(0).toInstant())
                .localIds(List.of("local-id-test"))
                .policy("mix")
                .build();
        List<SendResult> res = api.sendArray(req);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SendArray_Extended_Async() {
        SendArrayRequest req = SendArrayRequest.builder()
                .senders(List.of(sender))
                .receptors(List.of(receptor))
                .messages(List.of("تست ارسال پیشرفته با خط‌مشی"))
                .types(List.of(MessageType.MOBILE_MEMORY))
                .date(new Date(0).toInstant())
                .localIds(List.of("local-id-test"))
                .policy("mix")
                .build();
        List<SendResult> res = api.sendArrayAsync(req).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Status_Single() {
        SendResult sendRes = api.send(sender, receptor, "تست بررسی وضعیت تکی");
        assertNotNull(sendRes);
        StatusResult status = api.getStatus(String.valueOf(sendRes.getMessageId()));
        assertNotNull(status);
    }

    @Test
    public void test_Sms_Status_Single_Async() {
        SendResult sendRes = api.sendAsync(sender, receptor, "تست بررسی وضعیت تکی").join();
        assertNotNull(sendRes);
        StatusResult status = api.getStatusAsync(String.valueOf(sendRes.getMessageId())).join();
        assertNotNull(status);
    }

    @Test
    public void test_Sms_Status_List() {
        SendResult sendRes = api.send(sender, receptor, "تست بررسی وضعیت لیست");
        assertNotNull(sendRes);
        List<StatusResult> statusList = api.getStatus(List.of(String.valueOf(sendRes.getMessageId())));
        assertNotNull(statusList);
    }

    @Test
    public void test_Sms_Status_List_Async() {
        SendResult sendRes = api.sendAsync(sender, receptor, "تست بررسی وضعیت لیست").join();
        assertNotNull(sendRes);
        List<StatusResult> statusList = api.getStatusAsync(List.of(String.valueOf(sendRes.getMessageId()))).join();
        assertNotNull(statusList);
    }

    @Test
    public void test_Sms_StatusLocalMessageId_Single() {
        String localId = "loc_" + System.currentTimeMillis();
        SendResult sendRes = api.send(sender, receptor, "تست بررسی وضعیت محلی تکی", localId);
        assertNotNull(sendRes);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        StatusLocalMessageIdResult localStatus = api.getStatusByLocalMessageId(localId);
        assertNotNull(localStatus);
    }

    @Test
    public void test_Sms_StatusLocalMessageId_Single_Async() {
        String localId = "loc_" + System.currentTimeMillis();
        SendResult sendRes = api.sendAsync(sender, receptor, "تست بررسی وضعیت محلی تکی", localId).join();
        assertNotNull(sendRes);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        StatusLocalMessageIdResult localStatus = api.getStatusByLocalMessageIdAsync(localId).join();
        assertNotNull(localStatus);
    }

    @Test
    public void test_Sms_StatusLocalMessageId_List() {
        String localId = "loc_list_" + System.currentTimeMillis();
        SendResult sendRes = api.send(sender, receptor, "تست بررسی وضعیت محلی لیست", localId);
        assertNotNull(sendRes);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<StatusLocalMessageIdResult> localStatusList = api.getStatusByLocalMessageId(List.of(localId));
        assertNotNull(localStatusList);
    }

    @Test
    public void test_Sms_StatusLocalMessageId_List_Async() {
        String localId = "loc_list_" + System.currentTimeMillis();
        SendResult sendRes = api.sendAsync(sender, receptor, "تست بررسی وضعیت محلی لیست", localId).join();
        assertNotNull(sendRes);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        List<StatusLocalMessageIdResult> localStatusList = api.getStatusByLocalMessageIdAsync(List.of(localId)).join();
        assertNotNull(localStatusList);
    }

    @Test
    public void test_Sms_Cancel_Single() {
        Date futureDate = new Date(System.currentTimeMillis() + 7200000L);
        List<SendResult> sendRes = api.send(sender, List.of(receptor), "تست لغو پیام زمان‌بندی شده تکی", MessageType.MOBILE_MEMORY, futureDate);
        assertNotNull(sendRes);
        if (!sendRes.isEmpty()) {
            StatusResult cancel = api.cancelMessages(String.valueOf(sendRes.get(0).getMessageId()));
            assertNotNull(cancel);
        }
    }

    @Test
    public void test_Sms_Cancel_Single_Async() {
        Date futureDate = new Date(System.currentTimeMillis() + 7200000L);
        List<SendResult> sendRes = api.sendAsync(sender, List.of(receptor), "تست لغو پیام زمان‌بندی شده تکی", futureDate).join();
        assertNotNull(sendRes);
        if (!sendRes.isEmpty()) {
            StatusResult cancel = api.cancelMessagesAsync(String.valueOf(sendRes.get(0).getMessageId())).join();
            assertNotNull(cancel);
        }
    }

    @Test
    public void test_Sms_Cancel_List() {
        Date futureDate = new Date(System.currentTimeMillis() + 7200000L);
        List<SendResult> sendRes = api.send(sender, List.of(receptor), "تست لغو پیام زمان‌بندی شده لیست", MessageType.MOBILE_MEMORY, futureDate);
        assertNotNull(sendRes);
        if (!sendRes.isEmpty()) {
            List<StatusResult> cancelList = api.cancelMessages(List.of(String.valueOf(sendRes.get(0).getMessageId())));
            assertNotNull(cancelList);
        }
    }

    @Test
    public void test_Sms_Cancel_List_Async() {
        Date futureDate = new Date(System.currentTimeMillis() + 7200000L);
        List<SendResult> sendRes = api.sendAsync(sender, List.of(receptor), "تست لغو پیام زمان‌بندی شده لیست", futureDate).join();
        assertNotNull(sendRes);
        if (!sendRes.isEmpty()) {
            List<StatusResult> cancelList = api.cancelMessagesAsync(List.of(String.valueOf(sendRes.get(0).getMessageId()))).join();
            assertNotNull(cancelList);
        }
    }

    @Test
    public void test_Sms_Select_Single() {
        SendResult sendRes = api.send(sender, receptor, "تست انتخاب تکی");
        assertNotNull(sendRes);
        SendResult selectSingle = api.selectMessages(String.valueOf(sendRes.getMessageId()));
        assertNotNull(selectSingle);
    }

    @Test
    public void test_Sms_Select_Single_Async() {
        SendResult sendRes = api.sendAsync(sender, receptor, "تست انتخاب تکی").join();
        assertNotNull(sendRes);
        SendResult selectSingle = api.selectMessagesAsync(String.valueOf(sendRes.getMessageId())).join();
        assertNotNull(selectSingle);
    }

    @Test
    public void test_Sms_Select_List() {
        SendResult sendRes = api.send(sender, receptor, "تست انتخاب لیست");
        assertNotNull(sendRes);
        List<SendResult> selectList = api.selectMessages(List.of(String.valueOf(sendRes.getMessageId())));
        assertNotNull(selectList);
    }

    @Test
    public void test_Sms_Select_List_Async() {
        SendResult sendRes = api.sendAsync(sender, receptor, "تست انتخاب لیست").join();
        assertNotNull(sendRes);
        List<SendResult> selectList = api.selectMessagesAsync(List.of(String.valueOf(sendRes.getMessageId()))).join();
        assertNotNull(selectList);
    }

    @Test
    public void test_Sms_SelectOutbox_1() {
        List<SendResult> res = api.selectOutbox(new Date(System.currentTimeMillis() - 86400000L));
        assertNotNull(res);
    }

    @Test
    public void test_Sms_SelectOutbox_1_Async() {
        List<SendResult> res = api.selectOutboxAsync(new Date(System.currentTimeMillis() - 86400000L)).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_LatestOutbox_1() {
        List<SendResult> res = api.getLatestOutbox(10);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_LatestOutbox_1_Async() {
        List<SendResult> res = api.getLatestOutboxAsync(10).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_CountOutbox_1() {
        CountOutboxResult res = api.countOutbox(new Date(System.currentTimeMillis() - 86400000L));
        assertNotNull(res);
    }

    @Test
    public void test_Sms_CountOutbox_1_Async() {
        CountOutboxResult res = api.countOutboxAsync(new Date(System.currentTimeMillis() - 86400000L)).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Receive() {
        List<ReceiveResult> res = api.receiveMessages(sender, 0);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Receive_Async() {
        List<ReceiveResult> res = api.receiveMessagesAsync(sender, 0).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_CountInbox_1() {
        CountInboxResult res = api.countInbox(new Date(System.currentTimeMillis() - 86400000L), sender);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_CountInbox_1_Async() {
        CountInboxResult res = api.countInboxAsync(new Date(System.currentTimeMillis() - 86400000L), sender).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_StatusByReceptor() {
        List<StatusResult> res = api.getStatusByReceptor(receptor, new Date(), null);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_StatusByReceptor_Async() {
        List<StatusResult> res = api.getStatusByReceptorAsync(receptor, new Date(), null).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Unreads() {
        List<ReceiveResult> res = api.getUnreadMessages(sender, 0);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_Unreads_Async() {
        List<ReceiveResult> res = api.getUnreadMessagesAsync(sender, 0).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_InboxPaged() {
        InboxPagedResult res = api.getInboxPage(sender, 0, null, null, 1);
        assertNotNull(res);
    }

    @Test
    public void test_Sms_InboxPaged_Async() {
        InboxPagedResult res = api.getInboxPageAsync(sender, 0, null, null, 1).join();
        assertNotNull(res);
    }

    @Test
    public void test_Sms_GroupSendReport() {
        List<GroupSendReportResult> res = api.getGroupSendReport(new Date(System.currentTimeMillis() - 86400000L), new Date());
        assertNotNull(res);
    }

    @Test
    public void test_Sms_GroupSendReport_Async() {
        List<GroupSendReportResult> res = api.getGroupSendReportAsync(new Date(System.currentTimeMillis() - 86400000L), new Date()).join();
        assertNotNull(res);
    }
}
