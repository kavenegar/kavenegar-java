package com.kavenegar.sdk;

import com.kavenegar.sdk.enums.MessageType;
import com.kavenegar.sdk.enums.VerifyLookupType;
import com.kavenegar.sdk.models.*;

import java.util.Date;
import java.util.List;
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

    CompletableFuture<List<StatusLocalMessageIdResult>> getStatusByLocalMessageIdAsync(List<String> messageIds);
    List<StatusLocalMessageIdResult> getStatusByLocalMessageId(List<String> messageIds);

    CompletableFuture<StatusLocalMessageIdResult> getStatusByLocalMessageIdAsync(String messageId);
    StatusLocalMessageIdResult getStatusByLocalMessageId(String messageId);

    CompletableFuture<List<SendResult>> selectMessagesAsync(List<String> messageIds);
    List<SendResult> selectMessages(List<String> messageIds);

    CompletableFuture<SendResult> selectMessagesAsync(String messageId);
    SendResult selectMessages(String messageId);

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

    CompletableFuture<List<ReceiveResult>> receiveMessagesAsync(String line, int isRead);
    List<ReceiveResult> receiveMessages(String line, int isRead);

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

    CompletableFuture<List<ReceiveResult>> getUnreadMessagesAsync(String lineNumber, int isRead);
    List<ReceiveResult> getUnreadMessages(String lineNumber, int isRead);

    CompletableFuture<InboxPagedResult> getInboxPageAsync(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber);
    InboxPagedResult getInboxPage(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber);

    CompletableFuture<List<GroupSendReportResult>> getGroupSendReportAsync(Date startDate, Date endDate);
    List<GroupSendReportResult> getGroupSendReport(Date startDate, Date endDate);

    // --- ACCOUNT ---
    CompletableFuture<AccountInfoResult> getAccountInfoAsync();
    AccountInfoResult getAccountInfo();

    CompletableFuture<AccountConfigResult> configureAccountAsync(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed);
    AccountConfigResult configureAccount(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed);

    // --- VERIFY ---
    CompletableFuture<SendResult> verifyLookupAsync(VerifyLookupRequest request);
    SendResult verifyLookup(VerifyLookupRequest request);

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

    CompletableFuture<TemplateInfoResult> addTemplateAsync(TemplateRequest request);
    TemplateInfoResult addTemplate(TemplateRequest request);

    CompletableFuture<TemplateInfoResult> addTemplateAsync(TemplateDto dto);
    TemplateInfoResult addTemplate(TemplateDto dto);

    CompletableFuture<TemplateInfoResult> updateTemplateAsync(int templateId, TemplateRequest request);
    TemplateInfoResult updateTemplate(int templateId, TemplateRequest request);

    CompletableFuture<TemplateInfoResult> updateTemplateAsync(int templateId, TemplateDto dto);
    TemplateInfoResult updateTemplate(int templateId, TemplateDto dto);

    CompletableFuture<TemplateResult> getTemplateAsync(int id, String apiKey, String localId);
    TemplateResult getTemplate(int id, String apiKey, String localId);

    CompletableFuture<String> deleteTemplateAsync(int id, String apiKey, String localId);
    String deleteTemplate(int id, String apiKey, String localId);

    // --- CALL ---
    CompletableFuture<List<SendResult>> makeTtsCallAsync(TtsRequest request);
    List<SendResult> makeTtsCall(TtsRequest request);

    CompletableFuture<SendResult> makeTtsCallAsync(String message, String receptor);
    SendResult makeTtsCall(String message, String receptor);

    CompletableFuture<List<SendResult>> makeTtsCallAsync(String message, List<String> receptors);
    List<SendResult> makeTtsCall(String message, List<String> receptors);

    CompletableFuture<List<SendResult>> makeTtsCallAsync(String message, List<String> receptors, Date date, List<String> localIds, String tag);
    List<SendResult> makeTtsCall(String message, List<String> receptors, Date date, List<String> localIds, String tag);

    // --- SUBCLIENT ---
    CompletableFuture<SubClientResult> addClientAsync(SubClientRequest request);
    SubClientResult addClient(SubClientRequest request);

    CompletableFuture<SubClientResult> updateClientAsync(SubClientRequest request);
    SubClientResult updateClient(SubClientRequest request);

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

    CompletableFuture<MediaDeleteResult> deleteMediaAsync(String id);
    MediaDeleteResult deleteMedia(String id);

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
