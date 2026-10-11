package com.kavenegar.sdk;

import com.kavenegar.sdk.enums.MessageType;
import com.kavenegar.sdk.enums.VerifyLookupType;
import com.kavenegar.sdk.models.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface KavenegarApi {

    // --- SMS ---
    CompletableFuture<List<SendResult>> sendAsync(SendRequest request);
    List<SendResult> send(SendRequest request);

    CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message);
    List<SendResult> send(String sender, List<String> receptors, String message);

    CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message);
    SendResult send(String sender, String receptor, String message);

    CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, Date date);
    SendResult send(String sender, String receptor, String message, MessageType type, Date date);

    CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, Date date);
    List<SendResult> send(String sender, List<String> receptors, String message, MessageType type, Date date);

    CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, Date date, String localId);
    SendResult send(String sender, String receptor, String message, MessageType type, Date date, String localId);

    CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, String localId);
    SendResult send(String sender, String receptor, String message, String localId);

    CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, String localId);
    List<SendResult> send(String sender, List<String> receptors, String message, String localId);

    CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, Date date, List<String> localIds, String tag, String text, String hide, String localMessageId, String policy, String mediaId);
    List<SendResult> send(String sender, List<String> receptors, String message, MessageType type, Date date, List<String> localIds, String tag, String text, String hide, String localMessageId, String policy, String mediaId);

    default CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, MessageType type, Date date, String tag, String text, String hide, String localMessageId, String policy, String mediaId) {
        return sendAsync(SendRequest.builder()
                .sender(sender)
                .receptors(receptors)
                .message(message)
                .type(type)
                .date(date != null ? date.toInstant() : null)
                .tag(tag)
                .text(text)
                .hide(hide)
                .localMessageId(localMessageId)
                .policy(policy)
                .mediaId(mediaId)
                .build());
    }

    default List<SendResult> send(String sender, List<String> receptors, String message, MessageType type, Date date, String tag, String text, String hide, String localMessageId, String policy, String mediaId) {
        return send( sender, receptors, message, type, date, null, tag, text, hide, localMessageId, policy, mediaId);
    }

    CompletableFuture<List<SendResult>> sendArrayAsync(SendArrayRequest request);
    List<SendResult> sendArray(SendArrayRequest request);

    CompletableFuture<List<SendResult>> sendArrayAsync(List<String> senders, List<String> receptors, List<String> messages);
    List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages);

    CompletableFuture<List<SendResult>> sendArrayAsync(String sender, List<String> receptors, List<String> messages, Date date);
    List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, MessageType type, Date date);

    CompletableFuture<List<SendResult>> sendArrayAsync(String sender, List<String> receptors, List<String> messages, Date date, String localMessageIds);
    List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, MessageType type, Date date, String localMessageIds);

    CompletableFuture<List<SendResult>> sendArrayAsync(String sender, List<String> receptors, List<String> messages, String localMessageId);
    List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, String localMessageId);

    CompletableFuture<List<SendResult>> sendArrayAsync(List<String> senders, List<String> receptors, List<String> messages, String localMessageId);
    List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages, String localMessageId);

    CompletableFuture<List<SendResult>> sendArrayAsync(List<String> senders, List<String> receptors, List<String> messages, Date date, List<String> localMessageIds, String tag, String hide, String policy, String mediaId);
    List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages, List<MessageType> types, Date date, List<String> localMessageIds, String tag, String hide, String policy, String mediaId);

    CompletableFuture<List<StatusResult>> getStatusAsync(List<String> messageIds);
    List<StatusResult> getStatus(List<String> messageIds);

    CompletableFuture<StatusResult> getStatusAsync(String messageId);
    StatusResult getStatus(String messageId);

    default CompletableFuture<List<StatusResult>> statusAsync(List<String> messageIds) { return getStatusAsync(messageIds); }
    default List<StatusResult> status(List<String> messageIds) { return getStatus(messageIds); }
    default CompletableFuture<StatusResult> statusAsync(String messageId) { return getStatusAsync(messageId); }
    default StatusResult status(String messageId) { return getStatus(messageId); }

    CompletableFuture<List<StatusLocalMessageIdResult>> getStatusByLocalMessageIdAsync(List<String> messageIds);
    List<StatusLocalMessageIdResult> getStatusByLocalMessageId(List<String> messageIds);

    CompletableFuture<StatusLocalMessageIdResult> getStatusByLocalMessageIdAsync(String messageId);
    StatusLocalMessageIdResult getStatusByLocalMessageId(String messageId);

    default CompletableFuture<List<StatusLocalMessageIdResult>> statusLocalMessageIdAsync(List<String> messageIds) { return getStatusByLocalMessageIdAsync(messageIds); }
    default List<StatusLocalMessageIdResult> statusLocalMessageId(List<String> messageIds) { return getStatusByLocalMessageId(messageIds); }
    default CompletableFuture<StatusLocalMessageIdResult> statusLocalMessageIdAsync(String messageId) { return getStatusByLocalMessageIdAsync(messageId); }
    default StatusLocalMessageIdResult statusLocalMessageId(String messageId) { return getStatusByLocalMessageId(messageId); }

    CompletableFuture<List<SendResult>> selectMessagesAsync(List<String> messageIds);
    List<SendResult> selectMessages(List<String> messageIds);

    CompletableFuture<SendResult> selectMessagesAsync(String messageId);
    SendResult selectMessages(String messageId);

    default CompletableFuture<List<SendResult>> selectAsync(List<String> messageIds) { return selectMessagesAsync(messageIds); }
    default List<SendResult> select(List<String> messageIds) { return selectMessages(messageIds); }
    default CompletableFuture<SendResult> selectAsync(String messageId) { return selectMessagesAsync(messageId); }
    default SendResult select(String messageId) { return selectMessages(messageId); }

    CompletableFuture<List<SendResult>> selectOutboxAsync(Date startDate);
    List<SendResult> selectOutbox(Date startDate);

    CompletableFuture<List<SendResult>> selectOutboxAsync(Date startDate, Date endDate);
    List<SendResult> selectOutbox(Date startDate, Date endDate);

    CompletableFuture<List<SendResult>> selectOutboxAsync(Date startDate, Date endDate, String sender);
    List<SendResult> selectOutbox(Date startDate, Date endDate, String sender);

    CompletableFuture<List<SendResult>> getLatestOutboxAsync(long pageSize);
    List<SendResult> getLatestOutbox(long pageSize);

    CompletableFuture<List<SendResult>> getLatestOutboxAsync(long pageSize, String sender);
    List<SendResult> getLatestOutbox(long pageSize, String sender);

    default CompletableFuture<List<SendResult>> latestOutboxAsync(long pageSize) { return getLatestOutboxAsync(pageSize); }
    default List<SendResult> latestOutbox(long pageSize) { return getLatestOutbox(pageSize); }
    default CompletableFuture<List<SendResult>> latestOutboxAsync(long pageSize, String sender) { return getLatestOutboxAsync(pageSize, sender); }
    default List<SendResult> latestOutbox(long pageSize, String sender) { return getLatestOutbox(pageSize, sender); }

    CompletableFuture<CountOutboxResult> countOutboxAsync(Date startDate);
    CountOutboxResult countOutbox(Date startDate);

    CompletableFuture<CountOutboxResult> countOutboxAsync(Date startDate, Date endDate);
    CountOutboxResult countOutbox(Date startDate, Date endDate);

    CompletableFuture<CountOutboxResult> countOutboxAsync(Date startDate, Date endDate, int status);
    CountOutboxResult countOutbox(Date startDate, Date endDate, int status);

    CompletableFuture<List<StatusResult>> cancelMessagesAsync(List<String> ids);
    List<StatusResult> cancelMessages(List<String> ids);

    CompletableFuture<StatusResult> cancelMessagesAsync(String messageId);
    StatusResult cancelMessages(String messageId);

    default CompletableFuture<List<StatusResult>> cancelAsync(List<String> ids) { return cancelMessagesAsync(ids); }
    default List<StatusResult> cancel(List<String> ids) { return cancelMessages(ids); }
    default CompletableFuture<StatusResult> cancelAsync(String messageId) { return cancelMessagesAsync(messageId); }
    default StatusResult cancel(String messageId) { return cancelMessages(messageId); }

    CompletableFuture<List<ReceiveResult>> receiveMessagesAsync(String line, int isRead);
    List<ReceiveResult> receiveMessages(String line, int isRead);

    default CompletableFuture<List<ReceiveResult>> receiveAsync(String line, int isRead) { return receiveMessagesAsync(line, isRead); }
    default List<ReceiveResult> receive(String line, int isRead) { return receiveMessages(line, isRead); }

    CompletableFuture<CountInboxResult> countInboxAsync(Date startDate, String lineNumber);
    CountInboxResult countInbox(Date startDate, String lineNumber);

    CompletableFuture<CountInboxResult> countInboxAsync(Date startDate, Date endDate, String lineNumber);
    CountInboxResult countInbox(Date startDate, Date endDate, String lineNumber);

    CompletableFuture<CountInboxResult> countInboxAsync(Date startDate, Date endDate, String lineNumber, int isRead);
    CountInboxResult countInbox(Date startDate, Date endDate, String lineNumber, int isRead);

    CompletableFuture<List<StatusResult>> getStatusByReceptorAsync(String receptor);
    List<StatusResult> getStatusByReceptor(String receptor);

    CompletableFuture<List<StatusResult>> getStatusByReceptorAsync(String receptor, Date startDate);
    List<StatusResult> getStatusByReceptor(String receptor, Date startDate);

    CompletableFuture<List<StatusResult>> getStatusByReceptorAsync(String receptor, Date startDate, Date endDate);
    List<StatusResult> getStatusByReceptor(String receptor, Date startDate, Date endDate);

    default CompletableFuture<List<StatusResult>> statusByReceptorAsync(String receptor) { return getStatusByReceptorAsync(receptor); }
    default List<StatusResult> statusByReceptor(String receptor) { return getStatusByReceptor(receptor); }
    default CompletableFuture<List<StatusResult>> statusByReceptorAsync(String receptor, Date startDate) { return getStatusByReceptorAsync(receptor, startDate); }
    default List<StatusResult> statusByReceptor(String receptor, Date startDate) { return getStatusByReceptor(receptor, startDate); }
    default CompletableFuture<List<StatusResult>> statusByReceptorAsync(String receptor, Date startDate, Date endDate) { return getStatusByReceptorAsync(receptor, startDate, endDate); }
    default List<StatusResult> statusByReceptor(String receptor, Date startDate, Date endDate) { return getStatusByReceptor(receptor, startDate, endDate); }

    CompletableFuture<List<ReceiveResult>> getUnreadMessagesAsync(String lineNumber, int isRead);
    List<ReceiveResult> getUnreadMessages(String lineNumber, int isRead);

    default CompletableFuture<List<ReceiveResult>> unreadsAsync(String lineNumber, int isRead) { return getUnreadMessagesAsync(lineNumber, isRead); }
    default List<ReceiveResult> unreads(String lineNumber, int isRead) { return getUnreadMessages(lineNumber, isRead); }

    CompletableFuture<InboxPagedResult> getInboxPageAsync(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber);
    InboxPagedResult getInboxPage(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber);

    default CompletableFuture<InboxPagedResult> inboxPagedAsync(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber) { return getInboxPageAsync(lineNumber, isRead, startDate, endDate, pageNumber); }
    default InboxPagedResult inboxPaged(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber) { return getInboxPage(lineNumber, isRead, startDate, endDate, pageNumber); }

    CompletableFuture<List<GroupSendReportResult>> getGroupSendReportAsync(Date startDate, Date endDate);
    List<GroupSendReportResult> getGroupSendReport(Date startDate, Date endDate);

    default CompletableFuture<List<GroupSendReportResult>> groupSendReportAsync(Date startDate, Date endDate) { return getGroupSendReportAsync(startDate, endDate); }
    default List<GroupSendReportResult> groupSendReport(Date startDate, Date endDate) { return getGroupSendReport(startDate, endDate); }

    // --- ACCOUNT ---
    CompletableFuture<AccountInfoResult> getAccountInfoAsync();
    AccountInfoResult getAccountInfo();

    default CompletableFuture<AccountInfoResult> accountInfoAsync() { return getAccountInfoAsync(); }
    default AccountInfoResult accountInfo() { return getAccountInfo(); }

    CompletableFuture<AccountConfigResult> configureAccountAsync(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed);
    AccountConfigResult configureAccount(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed);

    default CompletableFuture<AccountConfigResult> accountConfigAsync(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed) {
        return configureAccountAsync(apiLogs, dailyReport, debugMode, defaultSender, minCreditAlarm, resendFailed);
    }
    default AccountConfigResult accountConfig(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed) {
        return configureAccount(apiLogs, dailyReport, debugMode, defaultSender, minCreditAlarm, resendFailed);
    }

    // --- VERIFY ---
    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String template);
    SendResult verifyLookup(String receptor, String token, String template);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String template, VerifyLookupType type);
    SendResult verifyLookup(String receptor, String token, String template, VerifyLookupType type);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String template);
    SendResult verifyLookup(String receptor, String token, String token2, String template);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String template, VerifyLookupType type);
    SendResult verifyLookup(String receptor, String token, String token2, String template, VerifyLookupType type);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String template);
    SendResult verifyLookup(String receptor, String token, String token2, String token3, String template);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String token10, String template);
    SendResult verifyLookup(String receptor, String token, String token2, String token3, String token10, String template);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String template, VerifyLookupType type);
    SendResult verifyLookup(String receptor, String token, String token2, String token3, String template, VerifyLookupType type);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String token10, String template, VerifyLookupType type);
    SendResult verifyLookup(String receptor, String token, String token2, String token3, String token10, String template, VerifyLookupType type);

    CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String token10, String token20, String template, VerifyLookupType type, String tag);
    SendResult verifyLookup(String receptor, String token, String token2, String token3, String token10, String token20, String template, VerifyLookupType type, String tag);

    CompletableFuture<List<TemplateResult>> listTemplatesAsync(String apiKey, String localId, Integer page);
    List<TemplateResult> listTemplates(String apiKey, String localId, Integer page);

    CompletableFuture<CloneTemplateResult> cloneTemplateAsync(Integer sourceTemplateId, String sourceTemplateName, String newTemplateName, String apiKey, String localId);
    CloneTemplateResult cloneTemplate(Integer sourceTemplateId, String sourceTemplateName, String newTemplateName, String apiKey, String localId);

    CompletableFuture<TemplateInfoResult> addTemplateAsync(TemplateDto dto);
    TemplateInfoResult addTemplate(TemplateDto dto);

    CompletableFuture<TemplateInfoResult> updateTemplateAsync(int templateId, TemplateDto dto);
    TemplateInfoResult updateTemplate(int templateId, TemplateDto dto);

    CompletableFuture<TemplateResult> getTemplateAsync(int id, String apiKey, String localId);
    TemplateResult getTemplate(int id, String apiKey, String localId);

    CompletableFuture<String> deleteTemplateAsync(int id, String apiKey, String localId);
    String deleteTemplate(int id, String apiKey, String localId);

    // --- CALL ---
    CompletableFuture<SendResult> makeTtsCallAsync(String message, String receptor);
    SendResult makeTtsCall(String message, String receptor);

    CompletableFuture<List<SendResult>> makeTtsCallAsync(String message, List<String> receptors);
    List<SendResult> makeTtsCall(String message, List<String> receptors);

    CompletableFuture<List<SendResult>> makeTtsCallAsync(String message, List<String> receptors, Date date, List<String> localIds, String tag);
    List<SendResult> makeTtsCall(String message, List<String> receptors, Date date, List<String> localIds, String tag);

    default CompletableFuture<List<SendResult>> makeTtsCallAsync(String message, List<String> receptors, Date date, List<String> localIds) {
        return makeTtsCallAsync(message, receptors, date, localIds, null);
    }
    default List<SendResult> makeTtsCall(String message, List<String> receptors, Date date, List<String> localIds) {
        return makeTtsCall(message, receptors, date, localIds, null);
    }

    default CompletableFuture<SendResult> callMakeTTSAsync(String message, String receptor) { return makeTtsCallAsync(message, receptor); }
    default SendResult callMakeTTS(String message, String receptor) { return makeTtsCall(message, receptor); }
    default CompletableFuture<List<SendResult>> callMakeTTSAsync(String message, List<String> receptors) { return makeTtsCallAsync(message, receptors); }
    default List<SendResult> callMakeTTS(String message, List<String> receptors) { return makeTtsCall(message, receptors); }
    default CompletableFuture<List<SendResult>> callMakeTTSAsync(String message, List<String> receptors, Date date, List<String> localIds) { return makeTtsCallAsync(message, receptors, date, localIds, null); }
    default List<SendResult> callMakeTTS(String message, List<String> receptors, Date date, List<String> localIds) { return makeTtsCall(message, receptors, date, localIds, null); }
    default CompletableFuture<List<SendResult>> callMakeTTSAsync(String message, List<String> receptors, Date date, List<String> localIds, String tag) { return makeTtsCallAsync(message, receptors, date, localIds, tag); }
    default List<SendResult> callMakeTTS(String message, List<String> receptors, Date date, List<String> localIds, String tag) { return makeTtsCall(message, receptors, date, localIds, tag); }

    // --- SUBCLIENT ---
    CompletableFuture<SubClientResult> addClientAsync(SubClientDto dto);
    SubClientResult addClient(SubClientDto dto);

    CompletableFuture<SubClientResult> updateClientAsync(SubClientDto dto);
    SubClientResult updateClient(SubClientDto dto);

    CompletableFuture<List<SubClientResult>> listClientsAsync();
    List<SubClientResult> listClients();

    CompletableFuture<SubClientResult> fetchClientAsync(String apiKey);
    SubClientResult fetchClient(String apiKey);

    CompletableFuture<SubClientResult> fetchClientByLocalIdAsync(String localId);
    SubClientResult fetchClientByLocalId(String localId);

    CompletableFuture<SubClientResult> renewClientKeyAsync(String apiKey, String localId);
    SubClientResult renewClientKey(String apiKey, String localId);

    CompletableFuture<SubClientResult> setClientStatusAsync(String apiKey, int status);
    SubClientResult setClientStatus(String apiKey, int status);

    CompletableFuture<SubClientResult> chargeClientCreditAsync(String apiKey, long credit, String desc, Integer vat, String ip);
    SubClientResult chargeClientCredit(String apiKey, long credit, String desc, Integer vat, String ip);

    // --- BLACKLIST / LINE ---
    CompletableFuture<List<BlacklistResult>> addBlockedLineAsync(String receptor, String lineNumber);
    List<BlacklistResult> addBlockedLine(String receptor, String lineNumber);

    CompletableFuture<RemoveBlacklistResult> removeBlockedLineAsync(String receptor, String lineNumber);
    RemoveBlacklistResult removeBlockedLine(String receptor, String lineNumber);

    CompletableFuture<LineBlockListResult> listBlockedLinesAsync(String lineNumber, int pageNumber, long startDate, Byte blockReason);
    LineBlockListResult listBlockedLines(String lineNumber, int pageNumber, long startDate, Byte blockReason);

    CompletableFuture<List<BlacklistResult>> blockedLineExistsAsync(String lineNumber, String receptor);
    List<BlacklistResult> blockedLineExists(String lineNumber, String receptor);

    // --- MEDIA ---
    CompletableFuture<MediaResult> uploadMediaAsync(String filePath);
    MediaResult uploadMedia(String filePath);

    CompletableFuture<MediaResult> uploadMediaAsync(String name, byte[] fileBytes);
    MediaResult uploadMedia(String name, byte[] fileBytes);

    CompletableFuture<MediaListResult> listMediaAsync(Integer page, Integer size);
    MediaListResult listMedia(Integer page, Integer size);

    CompletableFuture<MediaResult> getMediaAsync(String id, String fileName);
    MediaResult getMedia(String id, String fileName);

    default CompletableFuture<MediaResult> getMediaAsync(UUID id, String fileName) {
        return getMediaAsync(id != null ? id.toString() : null, fileName);
    }
    default MediaResult getMedia(UUID id, String fileName) {
        return getMedia(id != null ? id.toString() : null, fileName);
    }

    CompletableFuture<MediaDeleteResult> deleteMediaAsync(String id);
    MediaDeleteResult deleteMedia(String id);

    default CompletableFuture<MediaDeleteResult> deleteMediaAsync(UUID id) {
        return deleteMediaAsync(id != null ? id.toString() : null);
    }
    default MediaDeleteResult deleteMedia(UUID id) {
        return deleteMedia(id != null ? id.toString() : null);
    }

    // --- CONTACT & GROUP ---
    CompletableFuture<List<ContactResult>> addContactAsync(int groupId, String number, String title, String birthdate, String email, String tags);
    List<ContactResult> addContact(int groupId, String number, String title, String birthdate, String email, String tags);

    CompletableFuture<List<GroupResult>> addGroupAsync(String name, String tag);
    List<GroupResult> addGroup(String name, String tag);

    CompletableFuture<List<ContactResult>> removeContactAsync(String contactId, String mobile, Integer groupId);
    List<ContactResult> removeContact(String contactId, String mobile, Integer groupId);

    CompletableFuture<List<GroupResult>> removeGroupAsync(int groupId);
    List<GroupResult> removeGroup(int groupId);

    CompletableFuture<List<GroupResult>> listGroupsAsync();
    List<GroupResult> listGroups();

    CompletableFuture<List<GroupResult>> editGroupAsync(int groupId, String groupName, Byte status, String tag);
    List<GroupResult> editGroup(int groupId, String groupName, Byte status, String tag);

    CompletableFuture<List<GroupResult>> searchGroupsAsync(String tag);
    List<GroupResult> searchGroups(String tag);

    // --- UTILITY ---
    CompletableFuture<ServerDateResult> getServerDateAsync();
    ServerDateResult getServerDate();

    CompletableFuture<String> pingAsync();
    String ping();
}
