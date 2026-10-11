package com.kavenegar.sdk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavenegar.sdk.enums.*;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import com.kavenegar.sdk.excepctions.KavenegarApiException;
import com.kavenegar.sdk.excepctions.KavenegarHttpException;
import com.kavenegar.sdk.models.*;
import com.kavenegar.sdk.utils.ApiEnvelope;
import com.kavenegar.sdk.utils.UnixTime;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class KavenegarClient implements IKavenegarApi {

    private final KavenegarClientOptions options;
    private String apiKey;

    public KavenegarClient(String apiKey) {
        this(KavenegarClientOptions.builder().apiKey(apiKey).build());
    }

    public KavenegarClient(String apiKey, HttpClient httpClient) {
        this(KavenegarClientOptions.builder().apiKey(apiKey).httpClient(httpClient).build());
    }

    public KavenegarClient(HttpClient httpClient) {
        this.options = KavenegarClientOptions.builder().apiKey("").httpClient(httpClient).build();
        this.apiKey = "";
    }

    public KavenegarClient(KavenegarOptions options) {
        this(options != null ? options.toClientOptions() : KavenegarClientOptions.builder().build());
    }

    public KavenegarClient(KavenegarOptions options, HttpClient httpClient) {
        this(KavenegarClientOptions.builder()
                .apiKey(options != null ? options.getApiKey() : null)
                .baseUrl(options != null && options.getBaseUrl() != null ? options.getBaseUrl() : "https://api.kavenegar.com")
                .httpClient(httpClient)
                .build());
    }

    public KavenegarClient(KavenegarClientOptions options, HttpClient httpClient) {
        this(KavenegarClientOptions.builder()
                .apiKey(options.getApiKey())
                .baseUrl(options.getBaseUrl())
                .timeout(options.getTimeout())
                .objectMapper(options.getObjectMapper())
                .httpClient(httpClient)
                .build());
    }

    public KavenegarClient(KavenegarClientOptions options) {
        if (options.getApiKey() == null || options.getApiKey().trim().isEmpty()) {
            throw new IllegalArgumentException("API key must not be null or empty.");
        }
        this.options = options;
        this.apiKey = options.getApiKey();
    }

    public static KavenegarClientOptions.Builder builder() {
        return KavenegarClientOptions.builder();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private <T> T joinUnwrapped(CompletableFuture<T> future) {
        try {
            return future.join();
        } catch (CompletionException e) {
            if (e.getCause() instanceof RuntimeException re) {
                throw re;
            }
            throw e;
        }
    }

    private Date addOneDay(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    private String getMimeType(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return "application/octet-stream";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0) {
            return "application/octet-stream";
        }
        String extension = fileName.substring(dotIndex).toLowerCase(Locale.ROOT);
        return switch (extension) {
            case ".jpg", ".jpeg" -> "image/jpeg";
            case ".png" -> "image/png";
            case ".gif" -> "image/gif";
            case ".bmp" -> "image/bmp";
            case ".webp" -> "image/webp";
            case ".mp4" -> "video/mp4";
            case ".pdf" -> "application/pdf";
            case ".txt" -> "text/plain";
            default -> "application/octet-stream";
        };
    }

    // --- Core Request Dispatcher ---

    private <T> CompletableFuture<ApiEnvelope<T>> executeRequestEnvelope(String httpMethod, String folder, String action, Map<String, String> formParams, TypeReference<ApiEnvelope<T>> typeRef) {
        String baseUrl = String.format("%s/v1/%s/%s/%s.json",
                options.getBaseUrl().replaceAll("/+$", ""),
                getApiKey(),
                folder,
                action);

        StringBuilder queryOrBody = new StringBuilder();
        if (formParams != null) {
            for (Map.Entry<String, String> entry : formParams.entrySet()) {
                if (entry.getValue() != null) {
                    if (queryOrBody.length() > 0) {
                        queryOrBody.append("&");
                    }
                    queryOrBody.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                }
            }
        }

        HttpRequest.Builder reqBuilder = HttpRequest.newBuilder()
                .timeout(options.getTimeout())
                .header("User-Agent", "Kavenegar-Java-SDK/1.0.0");

        if ("GET".equalsIgnoreCase(httpMethod)) {
            String fullUrl = baseUrl + (queryOrBody.length() > 0 ? "?" + queryOrBody : "");
            reqBuilder.uri(URI.create(fullUrl)).GET();
        } else if ("DELETE".equalsIgnoreCase(httpMethod)) {
            String fullUrl = baseUrl + (queryOrBody.length() > 0 ? "?" + queryOrBody : "");
            reqBuilder.uri(URI.create(fullUrl)).method("DELETE", HttpRequest.BodyPublishers.noBody());
        } else { // POST
            reqBuilder.uri(URI.create(baseUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(queryOrBody.toString()));
        }

        HttpRequest request = reqBuilder.build();

        return options.getHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> handleResponseEnvelope(response, typeRef));
    }

    private <T> CompletableFuture<T> executeRequest(String httpMethod, String folder, String action, Map<String, String> formParams, TypeReference<ApiEnvelope<T>> typeRef) {
        return executeRequestEnvelope(httpMethod, folder, action, formParams, typeRef).thenApply(ApiEnvelope::getEntries);
    }

    private <T> CompletableFuture<T> executePost(String folder, String action, Map<String, String> formParams, TypeReference<ApiEnvelope<T>> typeRef) {
        return executeRequest("POST", folder, action, formParams, typeRef);
    }

    private <T> CompletableFuture<T> executeGet(String folder, String action, Map<String, String> formParams, TypeReference<ApiEnvelope<T>> typeRef) {
        return executeRequest("GET", folder, action, formParams, typeRef);
    }

    private <T> CompletableFuture<T> executeDelete(String folder, String action, Map<String, String> formParams, TypeReference<ApiEnvelope<T>> typeRef) {
        return executeRequest("DELETE", folder, action, formParams, typeRef);
    }

    private <T> CompletableFuture<T> executeMultipart(String folder, String action, String fieldName, String filename, byte[] fileBytes, Map<String, String> extraParams, TypeReference<ApiEnvelope<T>> typeRef) {
        String url = String.format("%s/v1/%s/%s/%s.json",
                options.getBaseUrl().replaceAll("/+$", ""),
                getApiKey(),
                folder,
                action);

        String boundary = "----KavenegarBoundary" + System.currentTimeMillis();
        byte[] multipartBody = createMultipartBody(boundary, fieldName, filename, fileBytes, extraParams);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(options.getTimeout())
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .header("User-Agent", "Kavenegar-Java-SDK/1.0.0")
                .POST(HttpRequest.BodyPublishers.ofByteArray(multipartBody))
                .build();

        return options.getHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> handleResponseEnvelope(response, typeRef).getEntries());
    }

    private <T> ApiEnvelope<T> handleResponseEnvelope(HttpResponse<String> response, TypeReference<ApiEnvelope<T>> typeRef) {
        String body = response.body();
        if (body != null && !body.trim().isEmpty() && body.trim().startsWith("{")) {
            try {
                ApiEnvelope<T> envelope = options.getObjectMapper().readValue(body, typeRef);
                if (envelope != null && envelope.getReturnHeader() != null) {
                    int status = envelope.getReturnHeader().getStatus();
                    if (status != 200) {
                        throw new ApiException(envelope.getReturnHeader().getMessage(), status);
                    }
                    return envelope;
                }
            } catch (KavenegarApiException e) {
                throw e;
            } catch (IOException e) {
                if (response.statusCode() == 200) {
                    throw new HttpException("Failed to parse JSON response: " + e.getMessage() + " | Body: " + body, response.statusCode(), e);
                }
            }
        }

        if (response.statusCode() != 200) {
            throw new HttpException("HTTP error status code: " + response.statusCode(), response.statusCode());
        }

        throw new HttpException("Empty or invalid response body: " + body, response.statusCode());
    }

    private byte[] createMultipartBody(String boundary, String fieldName, String filename, byte[] fileBytes, Map<String, String> extraParams) {
        ByteArrayBuilder builder = new ByteArrayBuilder();
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        if (extraParams != null) {
            for (Map.Entry<String, String> entry : extraParams.entrySet()) {
                if (entry.getValue() != null) {
                    builder.append((twoHyphens + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
                    builder.append(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + lineEnd + lineEnd).getBytes(StandardCharsets.UTF_8));
                    builder.append((entry.getValue() + lineEnd).getBytes(StandardCharsets.UTF_8));
                }
            }
        }

        String mimeType = getMimeType(filename);
        builder.append((twoHyphens + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
        builder.append(("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + filename + "\"" + lineEnd).getBytes(StandardCharsets.UTF_8));
        builder.append(("Content-Type: " + mimeType + lineEnd + lineEnd).getBytes(StandardCharsets.UTF_8));
        builder.append(fileBytes);
        builder.append(lineEnd.getBytes(StandardCharsets.UTF_8));
        builder.append((twoHyphens + boundary + twoHyphens + lineEnd).getBytes(StandardCharsets.UTF_8));

        return builder.toByteArray();
    }

    private static class ByteArrayBuilder {
        private byte[] buf = new byte[1024];
        private int count = 0;

        public void append(byte[] b) {
            ensureCapacity(count + b.length);
            System.arraycopy(b, 0, buf, count, b.length);
            count += b.length;
        }

        private void ensureCapacity(int minCapacity) {
            if (minCapacity - buf.length > 0) {
                int newCapacity = buf.length << 1;
                if (newCapacity - minCapacity < 0) newCapacity = minCapacity;
                buf = Arrays.copyOf(buf, newCapacity);
            }
        }

        public byte[] toByteArray() {
            return Arrays.copyOf(buf, count);
        }
    }

    private String join(List<String> items) {
        if (items == null || items.isEmpty()) return null;
        return String.join(",", items);
    }

    private String joinEnum(List<? extends Enum<?>> items) {
        if (items == null || items.isEmpty()) return null;
        return items.stream().map(e -> {
            if (e instanceof MessageType mt) return String.valueOf(mt.getValue());
            return e.name();
        }).collect(Collectors.joining(","));
    }

    private List<String> trimSendArrayValues(List<String> values) {
        if (values == null) return null;
        return values.stream().map(v -> v != null ? v.trim() : null).collect(Collectors.toList());
    }

    // --- SMS API IMPL ---

    @Override
    public CompletableFuture<List<SendResult>> sendAsync(SendRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("sender", request.getSender());
        params.put("receptor", join(request.getReceptors()));
        params.put("message", request.getMessage());
        if (request.getType() != null) params.put("type", String.valueOf(request.getType().getValue()));
        if (request.getDate() != null) params.put("date", String.valueOf(UnixTime.toEpochSeconds(request.getDate())));
        if (request.getLocalIds() != null && !request.getLocalIds().isEmpty()) params.put("localid", join(request.getLocalIds()));
        else if (request.getLocalId() != null) params.put("localid", request.getLocalId());
        if (request.getLocalMessageId() != null) params.put("localmessageid", request.getLocalMessageId());
        if (request.getTag() != null) params.put("tag", request.getTag());
        if (request.getText() != null) params.put("text", request.getText());
        if (request.getHideAsString() != null) params.put("hide", request.getHideAsString());
        if (request.getPolicy() != null) params.put("policy", request.getPolicy());
        if (request.getMediaId() != null) params.put("mediaid", request.getMediaId());

        return executePost("sms", "send", params, new TypeReference<>() {});
    }

    @Override
    public List<SendResult> send(SendRequest request) { return joinUnwrapped(sendAsync(request)); }

    @Override
    public CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message) {
        return sendAsync(SendRequest.builder().sender(sender).receptors(receptors).message(message).build());
    }

    @Override
    public List<SendResult> send(String sender, List<String> receptors, String message) { return joinUnwrapped(sendAsync(sender, receptors, message)); }

    @Override
    public CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message) {
        return sendAsync(sender, List.of(receptor), message).thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public SendResult send(String sender, String receptor, String message) { return joinUnwrapped(sendAsync(sender, receptor, message)); }

    @Override
    public CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, Date date) {
        return sendAsync(SendRequest.builder().sender(sender).receptor(receptor).message(message).date(date != null ? date.toInstant() : null).build())
                .thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    private CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, MessageType type, Date date) {
        return sendAsync(SendRequest.builder().sender(sender).receptor(receptor).message(message).type(type).date(date != null ? date.toInstant() : null).build())
                .thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public SendResult send(String sender, String receptor, String message, MessageType type, Date date) { return joinUnwrapped(sendAsync(sender, receptor, message, type, date)); }

    @Override
    public CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, Date date) {
        return sendAsync(SendRequest.builder().sender(sender).receptors(receptors).message(message).date(date != null ? date.toInstant() : null).build());
    }

    private CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, MessageType type, Date date) {
        return sendAsync(SendRequest.builder().sender(sender).receptors(receptors).message(message).type(type).date(date != null ? date.toInstant() : null).build());
    }

    @Override
    public List<SendResult> send(String sender, List<String> receptors, String message, MessageType type, Date date) { return joinUnwrapped(sendAsync(sender, receptors, message, type, date)); }

    @Override
    public CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, Date date, String localId) {
        return sendAsync(SendRequest.builder().sender(sender).receptor(receptor).message(message).date(date != null ? date.toInstant() : null).localId(localId).build())
                .thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    private CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, MessageType type, Date date, String localId) {
        return sendAsync(SendRequest.builder().sender(sender).receptor(receptor).message(message).type(type).date(date != null ? date.toInstant() : null).localId(localId).build())
                .thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public SendResult send(String sender, String receptor, String message, MessageType type, Date date, String localId) { return joinUnwrapped(sendAsync(sender, receptor, message, type, date, localId)); }

    @Override
    public CompletableFuture<SendResult> sendAsync(String sender, String receptor, String message, String localId) {
        return sendAsync(SendRequest.builder().sender(sender).receptor(receptor).message(message).localId(localId).build())
                .thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public SendResult send(String sender, String receptor, String message, String localId) { return joinUnwrapped(sendAsync(sender, receptor, message, localId)); }

    @Override
    public CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, String localId) {
        return sendAsync(SendRequest.builder().sender(sender).receptors(receptors).message(message).localId(localId).build());
    }

    @Override
    public List<SendResult> send(String sender, List<String> receptors, String message, String localId) { return joinUnwrapped(sendAsync(sender, receptors, message, localId)); }

    @Override
    public CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, Date date, List<String> localIds, String tag, String text, String hide, String localMessageId, String policy, String mediaId) {
        return sendAsync(SendRequest.builder().sender(sender).receptors(receptors).message(message).date(date != null ? date.toInstant() : null).localIds(localIds).tag(tag).text(text).hide(hide).localMessageId(localMessageId).policy(policy).mediaId(mediaId).build());
    }

    private CompletableFuture<List<SendResult>> sendAsync(String sender, List<String> receptors, String message, MessageType type, Date date, List<String> localIds, String tag, String text, String hide, String localMessageId, String policy, String mediaId) {
        return sendAsync(SendRequest.builder().sender(sender).receptors(receptors).message(message).type(type).date(date != null ? date.toInstant() : null).localIds(localIds).tag(tag).text(text).hide(hide).localMessageId(localMessageId).policy(policy).mediaId(mediaId).build());
    }

    @Override
    public List<SendResult> send(String sender, List<String> receptors, String message, MessageType type, Date date, List<String> localIds, String tag, String text, String hide, String localMessageId, String policy, String mediaId) {
        return joinUnwrapped(sendAsync(sender, receptors, message, type, date, localIds, tag, text, hide, localMessageId, policy, mediaId));
    }

    @Override
    public CompletableFuture<List<SendResult>> sendArrayAsync(SendArrayRequest request) {
        Map<String, String> params = new HashMap<>();
        try {
            if (request.getSenders() != null && !request.getSenders().isEmpty()) {
                params.put("sender", options.getObjectMapper().writeValueAsString(trimSendArrayValues(request.getSenders())));
            }
            if (request.getReceptors() != null && !request.getReceptors().isEmpty()) {
                params.put("receptor", options.getObjectMapper().writeValueAsString(trimSendArrayValues(request.getReceptors())));
            }
            if (request.getMessages() != null && !request.getMessages().isEmpty()) {
                params.put("message", options.getObjectMapper().writeValueAsString(request.getMessages()));
            }
            if (request.getTypes() != null && !request.getTypes().isEmpty()) {
                List<Integer> typeInts = request.getTypes().stream().map(MessageType::getValue).collect(Collectors.toList());
                params.put("type", options.getObjectMapper().writeValueAsString(typeInts));
            }
        } catch (Exception e) {
            CompletableFuture<List<SendResult>> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;
        }

        if (request.getDate() != null) params.put("date", String.valueOf(UnixTime.toEpochSeconds(request.getDate())));
        if (request.getLocalIds() != null && !request.getLocalIds().isEmpty()) {
            params.put("localmessageids", join(request.getLocalIds()));
        }
        if (request.getTag() != null) params.put("tag", request.getTag());
        if (request.getHideAsString() != null) params.put("hide", request.getHideAsString());
        if (request.getPolicy() != null) params.put("policy", request.getPolicy());
        if (request.getMediaId() != null) params.put("mediaid", request.getMediaId());

        return executePost("sms", "sendarray", params, new TypeReference<>() {});
    }

    @Override
    public List<SendResult> sendArray(SendArrayRequest request) { return joinUnwrapped(sendArrayAsync(request)); }

    @Override
    public CompletableFuture<List<SendResult>> sendArrayAsync(List<String> senders, List<String> receptors, List<String> messages) {
        return sendArrayAsync(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).build());
    }

    @Override
    public List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages) {
        List<MessageType> types = Collections.nCopies(senders != null ? senders.size() : 0, MessageType.MOBILE_MEMORY);
        return sendArray(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).types(types).build());
    }

    @Override
    public CompletableFuture<List<SendResult>> sendArrayAsync(String sender, List<String> receptors, List<String> messages, Date date) {
        List<String> senders = Collections.nCopies(receptors != null ? receptors.size() : 0, sender);
        return sendArrayAsync(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).date(date != null ? date.toInstant() : null).build());
    }

    @Override
    public List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, MessageType type, Date date) {
        List<String> senders = Collections.nCopies(receptors != null ? receptors.size() : 0, sender);
        List<MessageType> types = Collections.nCopies(receptors != null ? receptors.size() : 0, MessageType.MOBILE_MEMORY);
        return sendArray(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).types(types).date(date != null ? date.toInstant() : null).build());
    }

    @Override
    public CompletableFuture<List<SendResult>> sendArrayAsync(String sender, List<String> receptors, List<String> messages, Date date, String localMessageIds) {
        List<String> senders = Collections.nCopies(receptors != null ? receptors.size() : 0, sender);
        List<String> localIds = localMessageIds != null ? List.of(localMessageIds) : null;
        return sendArrayAsync(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).date(date != null ? date.toInstant() : null).localIds(localIds).build());
    }

    @Override
    public List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, MessageType type, Date date, String localMessageIds) {
        List<String> senders = Collections.nCopies(receptors != null ? receptors.size() : 0, sender);
        List<MessageType> types = Collections.nCopies(receptors != null ? receptors.size() : 0, MessageType.MOBILE_MEMORY);
        List<String> localIds = localMessageIds != null ? List.of(localMessageIds) : null;
        return sendArray(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).types(types).date(date != null ? date.toInstant() : null).localIds(localIds).build());
    }

    @Override
    public CompletableFuture<List<SendResult>> sendArrayAsync(String sender, List<String> receptors, List<String> messages, String localMessageId) {
        List<String> senders = Collections.nCopies(receptors != null ? receptors.size() : 0, sender);
        return sendArrayAsync(senders, receptors, messages, localMessageId);
    }

    @Override
    public List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, String localMessageId) {
        List<String> senders = Collections.nCopies(receptors != null ? receptors.size() : 0, sender);
        return sendArray(senders, receptors, messages, localMessageId);
    }

    @Override
    public CompletableFuture<List<SendResult>> sendArrayAsync(List<String> senders, List<String> receptors, List<String> messages, String localMessageId) {
        List<String> localIds = Collections.nCopies(receptors != null ? receptors.size() : 0, localMessageId);
        return sendArrayAsync(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).localIds(localIds).build());
    }

    @Override
    public List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages, String localMessageId) {
        List<MessageType> types = Collections.nCopies(receptors != null ? receptors.size() : 0, MessageType.MOBILE_MEMORY);
        List<String> localIds = Collections.nCopies(receptors != null ? receptors.size() : 0, localMessageId);
        return sendArray(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).types(types).localIds(localIds).build());
    }

    @Override
    public CompletableFuture<List<SendResult>> sendArrayAsync(List<String> senders, List<String> receptors, List<String> messages, Date date, List<String> localMessageIds, String tag, String hide, String policy, String mediaId) {
        return sendArrayAsync(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).date(date != null ? date.toInstant() : null).localIds(localMessageIds).tag(tag).hide(hide).policy(policy).mediaId(mediaId).build());
    }

    @Override
    public List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages, List<MessageType> types, Date date, List<String> localMessageIds, String tag, String hide, String policy, String mediaId) {
        return joinUnwrapped(sendArrayAsync(SendArrayRequest.builder().senders(senders).receptors(receptors).messages(messages).types(types).date(date != null ? date.toInstant() : null).localIds(localMessageIds).tag(tag).hide(hide).policy(policy).mediaId(mediaId).build()));
    }

    @Override
    public CompletableFuture<List<StatusResult>> getStatusAsync(List<String> messageIds) {
        Map<String, String> params = Map.of("messageid", join(messageIds));
        return executePost("sms", "status", params, new TypeReference<>() {});
    }

    @Override
    public List<StatusResult> getStatus(List<String> messageIds) { return joinUnwrapped(getStatusAsync(messageIds)); }

    @Override
    public CompletableFuture<StatusResult> getStatusAsync(String messageId) {
        return getStatusAsync(List.of(messageId)).thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public StatusResult getStatus(String messageId) { return joinUnwrapped(getStatusAsync(messageId)); }

    @Override
    public CompletableFuture<List<StatusLocalMessageIdResult>> getStatusByLocalMessageIdAsync(List<String> messageIds) {
        Map<String, String> params = Map.of("localid", join(messageIds));
        return executePost("sms", "statuslocalmessageid", params, new TypeReference<>() {});
    }

    @Override
    public List<StatusLocalMessageIdResult> getStatusByLocalMessageId(List<String> messageIds) { return joinUnwrapped(getStatusByLocalMessageIdAsync(messageIds)); }

    @Override
    public CompletableFuture<StatusLocalMessageIdResult> getStatusByLocalMessageIdAsync(String messageId) {
        return getStatusByLocalMessageIdAsync(List.of(messageId)).thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public StatusLocalMessageIdResult getStatusByLocalMessageId(String messageId) { return joinUnwrapped(getStatusByLocalMessageIdAsync(messageId)); }

    @Override
    public CompletableFuture<List<SendResult>> selectMessagesAsync(List<String> messageIds) {
        Map<String, String> params = Map.of("messageid", join(messageIds));
        return executePost("sms", "select", params, new TypeReference<>() {});
    }

    @Override
    public List<SendResult> selectMessages(List<String> messageIds) { return joinUnwrapped(selectMessagesAsync(messageIds)); }

    @Override
    public CompletableFuture<SendResult> selectMessagesAsync(String messageId) {
        return selectMessagesAsync(List.of(messageId)).thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public SendResult selectMessages(String messageId) { return joinUnwrapped(selectMessagesAsync(messageId)); }

    @Override
    public CompletableFuture<List<SendResult>> selectOutboxAsync(Date startDate) { return selectOutboxAsync(startDate, addOneDay(startDate), null); }

    @Override
    public List<SendResult> selectOutbox(Date startDate) { return joinUnwrapped(selectOutboxAsync(startDate)); }

    @Override
    public CompletableFuture<List<SendResult>> selectOutboxAsync(Date startDate, Date endDate) { return selectOutboxAsync(startDate, endDate, null); }

    @Override
    public List<SendResult> selectOutbox(Date startDate, Date endDate) { return joinUnwrapped(selectOutboxAsync(startDate, endDate)); }

    @Override
    public CompletableFuture<List<SendResult>> selectOutboxAsync(Date startDate, Date endDate, String sender) {
        Map<String, String> params = new HashMap<>();
        if (startDate != null) params.put("startdate", String.valueOf(UnixTime.toEpochSeconds(startDate)));
        if (endDate != null) params.put("enddate", String.valueOf(UnixTime.toEpochSeconds(endDate)));
        if (sender != null) params.put("sender", sender);
        return executePost("sms", "selectoutbox", params, new TypeReference<>() {});
    }

    @Override
    public List<SendResult> selectOutbox(Date startDate, Date endDate, String sender) { return joinUnwrapped(selectOutboxAsync(startDate, endDate, sender)); }

    @Override
    public CompletableFuture<List<SendResult>> getLatestOutboxAsync(long pageSize) { return getLatestOutboxAsync(pageSize, null); }

    @Override
    public List<SendResult> getLatestOutbox(long pageSize) { return joinUnwrapped(getLatestOutboxAsync(pageSize)); }

    @Override
    public CompletableFuture<List<SendResult>> getLatestOutboxAsync(long pageSize, String sender) {
        Map<String, String> params = new HashMap<>();
        params.put("pagesize", String.valueOf(pageSize));
        if (sender != null) params.put("sender", sender);
        return executePost("sms", "latestoutbox", params, new TypeReference<>() {});
    }

    @Override
    public List<SendResult> getLatestOutbox(long pageSize, String sender) { return joinUnwrapped(getLatestOutboxAsync(pageSize, sender)); }

    @Override
    public CompletableFuture<CountOutboxResult> countOutboxAsync(Date startDate) { return countOutboxAsync(startDate, addOneDay(startDate), 10); }

    @Override
    public CountOutboxResult countOutbox(Date startDate) { return joinUnwrapped(countOutboxAsync(startDate)); }

    @Override
    public CompletableFuture<CountOutboxResult> countOutboxAsync(Date startDate, Date endDate) { return countOutboxAsync(startDate, endDate, 0); }

    @Override
    public CountOutboxResult countOutbox(Date startDate, Date endDate) { return joinUnwrapped(countOutboxAsync(startDate, endDate)); }

    @Override
    public CompletableFuture<CountOutboxResult> countOutboxAsync(Date startDate, Date endDate, int status) {
        Map<String, String> params = new HashMap<>();
        if (startDate != null) params.put("startdate", String.valueOf(UnixTime.toEpochSeconds(startDate)));
        if (endDate != null) params.put("enddate", String.valueOf(UnixTime.toEpochSeconds(endDate)));
        if (status > 0) params.put("status", String.valueOf(status));
        return executePost("sms", "countoutbox", params, new TypeReference<>() {});
    }

    @Override
    public CountOutboxResult countOutbox(Date startDate, Date endDate, int status) { return joinUnwrapped(countOutboxAsync(startDate, endDate, status)); }

    @Override
    public CompletableFuture<List<StatusResult>> cancelMessagesAsync(List<String> ids) {
        Map<String, String> params = Map.of("messageid", join(ids));
        return executePost("sms", "cancel", params, new TypeReference<>() {});
    }

    @Override
    public List<StatusResult> cancelMessages(List<String> ids) { return joinUnwrapped(cancelMessagesAsync(ids)); }

    @Override
    public CompletableFuture<StatusResult> cancelMessagesAsync(String messageId) {
        return cancelMessagesAsync(List.of(messageId)).thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public StatusResult cancelMessages(String messageId) { return joinUnwrapped(cancelMessagesAsync(messageId)); }

    @Override
    public CompletableFuture<List<ReceiveResult>> receiveMessagesAsync(String line, int isRead) {
        Map<String, String> params = Map.of("linenumber", line, "isread", String.valueOf(isRead));
        return executeGet("sms", "receive", params, new TypeReference<>() {});
    }

    @Override
    public List<ReceiveResult> receiveMessages(String line, int isRead) { return joinUnwrapped(receiveMessagesAsync(line, isRead)); }

    @Override
    public CompletableFuture<CountInboxResult> countInboxAsync(Date startDate, String lineNumber) { return countInboxAsync(startDate, addOneDay(startDate), lineNumber, 0); }

    @Override
    public CountInboxResult countInbox(Date startDate, String lineNumber) { return joinUnwrapped(countInboxAsync(startDate, lineNumber)); }

    @Override
    public CompletableFuture<CountInboxResult> countInboxAsync(Date startDate, Date endDate, String lineNumber) { return countInboxAsync(startDate, endDate, lineNumber, 0); }

    @Override
    public CountInboxResult countInbox(Date startDate, Date endDate, String lineNumber) { return joinUnwrapped(countInboxAsync(startDate, endDate, lineNumber)); }

    @Override
    public CompletableFuture<CountInboxResult> countInboxAsync(Date startDate, Date endDate, String lineNumber, int isRead) {
        Map<String, String> params = new HashMap<>();
        if (startDate != null) params.put("startdate", String.valueOf(UnixTime.toEpochSeconds(startDate)));
        if (endDate != null) params.put("enddate", String.valueOf(UnixTime.toEpochSeconds(endDate)));
        params.put("linenumber", lineNumber);
        params.put("isread", String.valueOf(isRead));
        return executePost("sms", "countinbox", params, new TypeReference<>() {});
    }

    @Override
    public CountInboxResult countInbox(Date startDate, Date endDate, String lineNumber, int isRead) { return joinUnwrapped(countInboxAsync(startDate, endDate, lineNumber, isRead)); }

    @Override
    public CompletableFuture<List<StatusResult>> getStatusByReceptorAsync(String receptor) {
        Date startDate = new Date();
        return getStatusByReceptorAsync(receptor, startDate, addOneDay(startDate));
    }

    @Override
    public List<StatusResult> getStatusByReceptor(String receptor) { return joinUnwrapped(getStatusByReceptorAsync(receptor)); }

    @Override
    public CompletableFuture<List<StatusResult>> getStatusByReceptorAsync(String receptor, Date startDate) {
        if (startDate == null) startDate = new Date();
        return getStatusByReceptorAsync(receptor, startDate, addOneDay(startDate));
    }

    @Override
    public List<StatusResult> getStatusByReceptor(String receptor, Date startDate) { return joinUnwrapped(getStatusByReceptorAsync(receptor, startDate)); }

    @Override
    public CompletableFuture<List<StatusResult>> getStatusByReceptorAsync(String receptor, Date startDate, Date endDate) {
        Map<String, String> params = new HashMap<>();
        params.put("receptor", receptor);
        if (startDate == null) {
            startDate = new Date();
        }
        params.put("startdate", String.valueOf(UnixTime.toEpochSeconds(startDate)));
        if (endDate == null) {
            endDate = addOneDay(startDate);
        }
        params.put("enddate", String.valueOf(UnixTime.toEpochSeconds(endDate)));
        return executePost("sms", "statusbyreceptor", params, new TypeReference<>() {});
    }

    @Override
    public List<StatusResult> getStatusByReceptor(String receptor, Date startDate, Date endDate) { return joinUnwrapped(getStatusByReceptorAsync(receptor, startDate, endDate)); }

    @Override
    public CompletableFuture<List<ReceiveResult>> getUnreadMessagesAsync(String lineNumber, int isRead) {
        Map<String, String> params = Map.of("linenumber", lineNumber, "isread", String.valueOf(isRead));
        return executeGet("sms", "unreads", params, new TypeReference<>() {});
    }

    @Override
    public List<ReceiveResult> getUnreadMessages(String lineNumber, int isRead) { return joinUnwrapped(getUnreadMessagesAsync(lineNumber, isRead)); }

    @Override
    public CompletableFuture<InboxPagedResult> getInboxPageAsync(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber) {
        Map<String, String> params = new HashMap<>();
        params.put("linenumber", lineNumber);
        if (isRead != null) params.put("isread", String.valueOf(isRead));
        if (startDate != null) params.put("startdate", String.valueOf(UnixTime.toEpochSeconds(startDate)));
        if (endDate != null) params.put("enddate", String.valueOf(UnixTime.toEpochSeconds(endDate)));
        if (pageNumber != null) params.put("page", String.valueOf(pageNumber));

        return executeRequestEnvelope("GET", "sms", "inboxpaged", params, new TypeReference<ApiEnvelope<List<ReceiveResult>>>() {})
                .thenApply(envelope -> new InboxPagedResult(envelope.getMetadata(), envelope.getEntries()));
    }

    @Override
    public InboxPagedResult getInboxPage(String lineNumber, Integer isRead, Date startDate, Date endDate, Integer pageNumber) { return joinUnwrapped(getInboxPageAsync(lineNumber, isRead, startDate, endDate, pageNumber)); }

    @Override
    public CompletableFuture<List<GroupSendReportResult>> getGroupSendReportAsync(Date startDate, Date endDate) {
        Map<String, String> params = new HashMap<>();
        if (startDate != null) params.put("startdate", String.valueOf(UnixTime.toEpochSeconds(startDate)));
        if (endDate != null) params.put("enddate", String.valueOf(UnixTime.toEpochSeconds(endDate)));
        return executeGet("sms", "report/groupsend", params, new TypeReference<>() {});
    }

    @Override
    public List<GroupSendReportResult> getGroupSendReport(Date startDate, Date endDate) { return joinUnwrapped(getGroupSendReportAsync(startDate, endDate)); }

    // --- Account ---

    @Override
    public CompletableFuture<AccountInfoResult> getAccountInfoAsync() {
        return executeGet("account", "info", Collections.emptyMap(), new TypeReference<>() {});
    }

    @Override
    public AccountInfoResult getAccountInfo() { return joinUnwrapped(getAccountInfoAsync()); }

    @Override
    public CompletableFuture<AccountConfigResult> configureAccountAsync(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed) {
        Map<String, String> params = new HashMap<>();
        if (apiLogs != null) params.put("apilogs", apiLogs);
        if (dailyReport != null) params.put("dailyreport", dailyReport);
        if (debugMode != null) params.put("debugmode", debugMode);
        if (defaultSender != null) params.put("defaultsender", defaultSender);
        if (minCreditAlarm != null) params.put("mincreditalarm", String.valueOf(minCreditAlarm));
        if (resendFailed != null) params.put("resendfailed", resendFailed);

        return executePost("account", "config", params, new TypeReference<ApiEnvelope<List<AccountConfigResult>>>() {})
                .thenApply(list -> (list == null || list.isEmpty()) ? null : list.get(0));
    }

    @Override
    public AccountConfigResult configureAccount(String apiLogs, String dailyReport, String debugMode, String defaultSender, Integer minCreditAlarm, String resendFailed) {
        return joinUnwrapped(configureAccountAsync(apiLogs, dailyReport, debugMode, defaultSender, minCreditAlarm, resendFailed));
    }

    // --- Verification ---

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String token10, String token20, String template, VerifyLookupType type, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("receptor", receptor);
        params.put("token", token);
        if (token2 != null) params.put("token2", token2);
        if (token3 != null) params.put("token3", token3);
        if (token10 != null) params.put("token10", token10);
        if (token20 != null) params.put("token20", token20);
        params.put("template", template);
        if (type != null) params.put("type", String.valueOf(type.getValue()));
        if (tag != null) params.put("tag", tag);

        return executePost("verify", "lookup", params, new TypeReference<ApiEnvelope<List<SendResult>>>() {})
                .thenApply(list -> (list == null || list.isEmpty()) ? null : list.get(0));
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String token2, String token3, String token10, String token20, String template, VerifyLookupType type, String tag) {
        return joinUnwrapped(verifyLookupAsync(receptor, token, token2, token3, token10, token20, template, type, tag));
    }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String template) {
        return verifyLookupAsync(receptor, token, null, null, null, null, template, VerifyLookupType.SMS, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String template) { return joinUnwrapped(verifyLookupAsync(receptor, token, template)); }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String template, VerifyLookupType type) {
        return verifyLookupAsync(receptor, token, null, null, null, null, template, type, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String template, VerifyLookupType type) { return joinUnwrapped(verifyLookupAsync(receptor, token, template, type)); }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String template) {
        return verifyLookupAsync(receptor, token, token2, null, null, null, template, VerifyLookupType.SMS, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String token2, String template) { return joinUnwrapped(verifyLookupAsync(receptor, token, token2, template)); }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String template, VerifyLookupType type) {
        return verifyLookupAsync(receptor, token, token2, null, null, null, template, type, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String token2, String template, VerifyLookupType type) { return joinUnwrapped(verifyLookupAsync(receptor, token, token2, template, type)); }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String template) {
        return verifyLookupAsync(receptor, token, token2, token3, null, null, template, VerifyLookupType.SMS, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String token2, String token3, String template) { return joinUnwrapped(verifyLookupAsync(receptor, token, token2, token3, template)); }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String token10, String template) {
        return verifyLookupAsync(receptor, token, token2, token3, token10, null, template, VerifyLookupType.SMS, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String token2, String token3, String token10, String template) { return joinUnwrapped(verifyLookupAsync(receptor, token, token2, token3, token10, template)); }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String template, VerifyLookupType type) {
        return verifyLookupAsync(receptor, token, token2, token3, null, null, template, type, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String token2, String token3, String template, VerifyLookupType type) { return joinUnwrapped(verifyLookupAsync(receptor, token, token2, token3, template, type)); }

    @Override
    public CompletableFuture<SendResult> verifyLookupAsync(String receptor, String token, String token2, String token3, String token10, String template, VerifyLookupType type) {
        return verifyLookupAsync(receptor, token, token2, token3, token10, null, template, type, null);
    }

    @Override
    public SendResult verifyLookup(String receptor, String token, String token2, String token3, String token10, String template, VerifyLookupType type) { return joinUnwrapped(verifyLookupAsync(receptor, token, token2, token3, token10, template, type)); }

    @Override
    public CompletableFuture<List<TemplateResult>> listTemplatesAsync(String apiKey, String localId, Integer page) {
        Map<String, String> params = new HashMap<>();
        if (apiKey != null) params.put("apikey", apiKey);
        if (localId != null) params.put("localid", localId);
        if (page != null) params.put("page", String.valueOf(page));
        return executeGet("verify", "templatelist", params, new TypeReference<>() {});
    }

    @Override
    public List<TemplateResult> listTemplates(String apiKey, String localId, Integer page) { return joinUnwrapped(listTemplatesAsync(apiKey, localId, page)); }

    @Override
    public CompletableFuture<CloneTemplateResult> cloneTemplateAsync(Integer sourceTemplateId, String sourceTemplateName, String newTemplateName, String apiKey, String localId) {
        Map<String, String> params = new HashMap<>();
        if (sourceTemplateId != null) params.put("sourcetemplateid", String.valueOf(sourceTemplateId));
        if (sourceTemplateName != null) params.put("sourcetemplatename", sourceTemplateName);
        if (newTemplateName != null) params.put("newtemplatename", newTemplateName);
        if (apiKey != null) params.put("apikey", apiKey);
        if (localId != null) params.put("localid", localId);
        return executePost("verify", "clonetemplate", params, new TypeReference<>() {});
    }

    @Override
    public CloneTemplateResult cloneTemplate(Integer sourceTemplateId, String sourceTemplateName, String newTemplateName, String apiKey, String localId) { return joinUnwrapped(cloneTemplateAsync(sourceTemplateId, sourceTemplateName, newTemplateName, apiKey, localId)); }

    @Override
    public CompletableFuture<TemplateInfoResult> addTemplateAsync(TemplateDto dto) {
        Map<String, String> params = buildTemplateDtoParams(dto);
        return executePost("verify", "addtemplate", params, new TypeReference<>() {});
    }

    @Override
    public TemplateInfoResult addTemplate(TemplateDto dto) { return joinUnwrapped(addTemplateAsync(dto)); }

    @Override
    public CompletableFuture<TemplateInfoResult> updateTemplateAsync(int templateId, TemplateDto dto) {
        Map<String, String> params = buildTemplateDtoParams(dto);
        params.put("templateid", String.valueOf(templateId));
        return executePost("verify", "updatetemplate", params, new TypeReference<>() {});
    }

    @Override
    public TemplateInfoResult updateTemplate(int templateId, TemplateDto dto) { return joinUnwrapped(updateTemplateAsync(templateId, dto)); }

    private Map<String, String> buildTemplateDtoParams(TemplateDto dto) {
        Map<String, String> params = new HashMap<>();
        if (dto.getSourceType() != null) params.put("sourcetype", String.valueOf(dto.getSourceType().getValue()));
        if (dto.getSendMethod() != null) params.put("sendmethod", String.valueOf(dto.getSendMethod().getValue()));
        if (dto.getFallbackMethod() != null) params.put("fallbackmethod", String.valueOf(dto.getFallbackMethod().getValue()));
        if (dto.getPrimaryLineNumber() != null) params.put("primarylinenumber", dto.getPrimaryLineNumber());
        if (dto.getSecondaryLineNumber() != null) params.put("secondarylinenumber", dto.getSecondaryLineNumber());
        if (dto.getSwitchTtl() != null) params.put("switchttl", String.valueOf(dto.getSwitchTtl()));
        if (dto.getSourceUrl() != null) params.put("sourceurl", dto.getSourceUrl());
        if (dto.getSourceName() != null) params.put("sourcename", dto.getSourceName());
        if (dto.getName() != null) params.put("name", dto.getName());
        if (dto.getTextMessage() != null) params.put("textmessage", dto.getTextMessage());
        if (dto.getVoiceMessage() != null) params.put("voicemessage", dto.getVoiceMessage());
        if (dto.getLocalId() != null) params.put("localid", dto.getLocalId());
        if (dto.getApiKey() != null) params.put("apikey", dto.getApiKey());
        return params;
    }

    @Override
    public CompletableFuture<TemplateResult> getTemplateAsync(int id, String apiKey, String localId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        if (apiKey != null) params.put("apikey", apiKey);
        if (localId != null) params.put("localid", localId);
        return executeGet("verify", "gettemplate", params, new TypeReference<>() {});
    }

    @Override
    public TemplateResult getTemplate(int id, String apiKey, String localId) { return joinUnwrapped(getTemplateAsync(id, apiKey, localId)); }

    @Override
    public CompletableFuture<String> deleteTemplateAsync(int id, String apiKey, String localId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        if (apiKey != null) params.put("apikey", apiKey);
        if (localId != null) params.put("localid", localId);
        return executeRequestEnvelope("DELETE", "verify", "deletetemplate", params, new TypeReference<ApiEnvelope<Object>>() {})
                .thenApply(env -> env.getReturnHeader() != null ? env.getReturnHeader().getMessage() : null);
    }

    @Override
    public String deleteTemplate(int id, String apiKey, String localId) { return joinUnwrapped(deleteTemplateAsync(id, apiKey, localId)); }

    // --- Voice Calls (TTS) ---

    @Override
    public CompletableFuture<SendResult> makeTtsCallAsync(String message, String receptor) {
        return makeTtsCallAsync(message, List.of(receptor)).thenApply(list -> list == null || list.isEmpty() ? null : list.get(0));
    }

    @Override
    public SendResult makeTtsCall(String message, String receptor) { return joinUnwrapped(makeTtsCallAsync(message, receptor)); }

    @Override
    public CompletableFuture<List<SendResult>> makeTtsCallAsync(String message, List<String> receptors) {
        return makeTtsCallAsync(message, receptors, null, null, null);
    }

    @Override
    public List<SendResult> makeTtsCall(String message, List<String> receptors) { return joinUnwrapped(makeTtsCallAsync(message, receptors)); }

    @Override
    public CompletableFuture<List<SendResult>> makeTtsCallAsync(String message, List<String> receptors, Date date, List<String> localIds, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("message", message);
        params.put("receptor", join(receptors));
        if (date != null) params.put("date", String.valueOf(UnixTime.toEpochSeconds(date)));
        if (localIds != null && !localIds.isEmpty()) params.put("localid", join(localIds));
        if (tag != null) params.put("tag", tag);

        return executePost("call", "maketts", params, new TypeReference<>() {});
    }

    @Override
    public List<SendResult> makeTtsCall(String message, List<String> receptors, Date date, List<String> localIds, String tag) { return joinUnwrapped(makeTtsCallAsync(message, receptors, date, localIds, tag)); }

    // --- SubClient ---

    @Override
    public CompletableFuture<SubClientResult> addClientAsync(SubClientDto dto) {
        Map<String, String> params = buildSubClientParams(dto);
        return executePost("client", "add", params, new TypeReference<>() {});
    }

    @Override
    public SubClientResult addClient(SubClientDto dto) { return joinUnwrapped(addClientAsync(dto)); }

    @Override
    public CompletableFuture<SubClientResult> updateClientAsync(SubClientDto dto) {
        Map<String, String> params = buildSubClientParams(dto);
        return executePost("client", "update", params, new TypeReference<>() {});
    }

    @Override
    public SubClientResult updateClient(SubClientDto dto) { return joinUnwrapped(updateClientAsync(dto)); }

    private Map<String, String> buildSubClientParams(SubClientDto dto) {
        Map<String, String> params = new HashMap<>();
        if (dto.getApiKey() != null) params.put("apikey", dto.getApiKey());
        if (dto.getIp() != null) params.put("ip", dto.getIp());
        if (dto.getLocalId() != null) params.put("localid", dto.getLocalId());
        if (dto.getCredit() != null) params.put("credit", String.valueOf(dto.getCredit()));
        if (dto.getUserName() != null) params.put("username", dto.getUserName());
        if (dto.getPassword() != null) params.put("password", dto.getPassword());
        if (dto.getFullName() != null) params.put("fullname", dto.getFullName());
        if (dto.getPlanId() != null) params.put("planid", String.valueOf(dto.getPlanId()));
        if (dto.getMobile() != null) params.put("mobile", dto.getMobile());
        if (dto.getStatus() != null) params.put("status", dto.getStatus());
        if (dto.getMininumAllowedCredit() != null) params.put("mininumallowedcredit", String.valueOf(dto.getMininumAllowedCredit()));
        if (dto.getLines() != null) params.put("lines", dto.getLines());
        if (dto.getCanUseParentLines() != null) params.put("canuseparentlines", dto.getCanUseParentLines() ? "1" : "0");
        if (dto.getExpireDate() != null) params.put("expiredate", dto.getExpireDate());
        if (dto.getEnableLink() != null) params.put("enablelink", dto.getEnableLink() ? "1" : "0");
        if (dto.getCanCharge() != null) params.put("cancharge", dto.getCanCharge() ? "1" : "0");
        return params;
    }

    @Override
    public CompletableFuture<List<SubClientResult>> listClientsAsync() {
        return executeGet("client", "list", Collections.emptyMap(), new TypeReference<>() {});
    }

    @Override
    public List<SubClientResult> listClients() { return joinUnwrapped(listClientsAsync()); }

    @Override
    public CompletableFuture<SubClientResult> fetchClientAsync(String apiKey) {
        Map<String, String> params = Map.of("apikey", apiKey);
        return executeGet("client", "fetch", params, new TypeReference<>() {});
    }

    @Override
    public SubClientResult fetchClient(String apiKey) { return joinUnwrapped(fetchClientAsync(apiKey)); }

    @Override
    public CompletableFuture<SubClientResult> fetchClientByLocalIdAsync(String localId) {
        Map<String, String> params = Map.of("localid", localId);
        return executeGet("client", "fetchbylocalid", params, new TypeReference<>() {});
    }

    @Override
    public SubClientResult fetchClientByLocalId(String localId) { return joinUnwrapped(fetchClientByLocalIdAsync(localId)); }

    @Override
    public CompletableFuture<SubClientResult> renewClientKeyAsync(String apiKey, String localId) {
        Map<String, String> params = new HashMap<>();
        if (apiKey != null) params.put("apikey", apiKey);
        if (localId != null) params.put("localid", localId);
        return executePost("client", "renewkey", params, new TypeReference<>() {});
    }

    @Override
    public SubClientResult renewClientKey(String apiKey, String localId) { return joinUnwrapped(renewClientKeyAsync(apiKey, localId)); }

    @Override
    public CompletableFuture<SubClientResult> setClientStatusAsync(String apiKey, int status) {
        Map<String, String> params = Map.of("apikey", apiKey, "status", String.valueOf(status));
        return executePost("client", "setstatus", params, new TypeReference<>() {});
    }

    @Override
    public SubClientResult setClientStatus(String apiKey, int status) { return joinUnwrapped(setClientStatusAsync(apiKey, status)); }

    @Override
    public CompletableFuture<SubClientResult> chargeClientCreditAsync(String apiKey, long credit, String desc, Integer vat, String ip) {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", apiKey);
        params.put("credit", String.valueOf(credit));
        if (desc != null) params.put("desc", desc);
        if (vat != null) params.put("vat", String.valueOf(vat));
        if (ip != null) params.put("ip", ip);
        return executePost("client", "chargecredit", params, new TypeReference<>() {});
    }

    @Override
    public SubClientResult chargeClientCredit(String apiKey, long credit, String desc, Integer vat, String ip) { return joinUnwrapped(chargeClientCreditAsync(apiKey, credit, desc, vat, ip)); }

    // --- Blacklist ---

    @Override
    public CompletableFuture<List<BlacklistResult>> addBlockedLineAsync(String receptor, String lineNumber) {
        Map<String, String> params = Map.of("receptor", receptor, "linenumber", lineNumber);
        return executePost("line", "blocked/add", params, new TypeReference<>() {});
    }

    @Override
    public List<BlacklistResult> addBlockedLine(String receptor, String lineNumber) { return joinUnwrapped(addBlockedLineAsync(receptor, lineNumber)); }

    @Override
    public CompletableFuture<RemoveBlacklistResult> removeBlockedLineAsync(String receptor, String lineNumber) {
        Map<String, String> params = Map.of("receptor", receptor, "linenumber", lineNumber);
        return executeDelete("line", "blocked/remove", params, new TypeReference<>() {});
    }

    @Override
    public RemoveBlacklistResult removeBlockedLine(String receptor, String lineNumber) { return joinUnwrapped(removeBlockedLineAsync(receptor, lineNumber)); }

    @Override
    public CompletableFuture<LineBlockListResult> listBlockedLinesAsync(String lineNumber, int pageNumber, long startDate, Byte blockReason) {
        Map<String, String> params = new HashMap<>();
        params.put("linenumber", lineNumber);
        params.put("page", String.valueOf(pageNumber));
        params.put("startdate", String.valueOf(startDate));
        if (blockReason != null) params.put("blockreason", String.valueOf(blockReason));

        return executeRequestEnvelope("GET", "line", "blocked/list", params, new TypeReference<ApiEnvelope<List<BlockedLineDetail>>>() {})
                .thenApply(envelope -> new LineBlockListResult(envelope.getMetadata(), envelope.getEntries()));
    }

    @Override
    public LineBlockListResult listBlockedLines(String lineNumber, int pageNumber, long startDate, Byte blockReason) { return joinUnwrapped(listBlockedLinesAsync(lineNumber, pageNumber, startDate, blockReason)); }

    @Override
    public CompletableFuture<List<BlacklistResult>> blockedLineExistsAsync(String lineNumber, String receptor) {
        Map<String, String> params = Map.of("linenumber", lineNumber, "receptor", receptor);
        return executeGet("line", "blocked/exists", params, new TypeReference<>() {});
    }

    @Override
    public List<BlacklistResult> blockedLineExists(String lineNumber, String receptor) { return joinUnwrapped(blockedLineExistsAsync(lineNumber, receptor)); }

    // --- Media ---

    @Override
    public CompletableFuture<MediaResult> uploadMediaAsync(String filePath) {
        try {
            Path path = Path.of(filePath);
            byte[] fileBytes = Files.readAllBytes(path);
            String fileName = path.getFileName().toString();
            return uploadMediaAsync(fileName, fileBytes);
        } catch (IOException e) {
            CompletableFuture<MediaResult> future = new CompletableFuture<>();
            future.completeExceptionally(new HttpException("Failed to read file for upload: " + e.getMessage(), 0, e));
            return future;
        }
    }

    @Override
    public MediaResult uploadMedia(String filePath) { return joinUnwrapped(uploadMediaAsync(filePath)); }

    @Override
    public CompletableFuture<MediaResult> uploadMediaAsync(String name, byte[] fileBytes) {
        return executeMultipart("media", "upload", "file", name, fileBytes, null, new TypeReference<>() {});
    }

    @Override
    public MediaResult uploadMedia(String name, byte[] fileBytes) { return joinUnwrapped(uploadMediaAsync(name, fileBytes)); }

    @Override
    public CompletableFuture<MediaListResult> listMediaAsync(Integer page, Integer size) {
        Map<String, String> params = new HashMap<>();
        if (page != null) params.put("page", String.valueOf(page));
        if (size != null) params.put("size", String.valueOf(size));
        return executeGet("media", "list", params, new TypeReference<>() {});
    }

    @Override
    public MediaListResult listMedia(Integer page, Integer size) { return joinUnwrapped(listMediaAsync(page, size)); }

    @Override
    public CompletableFuture<MediaResult> getMediaAsync(String id, String fileName) {
        Map<String, String> params = new HashMap<>();
        if (id != null) params.put("id", id);
        if (fileName != null) params.put("filename", fileName);
        return executeGet("media", "get", params, new TypeReference<>() {});
    }

    @Override
    public MediaResult getMedia(String id, String fileName) { return joinUnwrapped(getMediaAsync(id, fileName)); }

    @Override
    public CompletableFuture<MediaDeleteResult> deleteMediaAsync(String id) {
        Map<String, String> params = Map.of("id", id);
        return executeDelete("media", "delete", params, new TypeReference<>() {});
    }

    @Override
    public MediaDeleteResult deleteMedia(String id) { return joinUnwrapped(deleteMediaAsync(id)); }

    // --- Contact & Group ---

    @Override
    public CompletableFuture<List<ContactResult>> addContactAsync(int groupId, String number, String title, String birthdate, String email, String tags) {
        Map<String, String> params = new HashMap<>();
        params.put("groupid", String.valueOf(groupId));
        params.put("number", number);
        if (title != null) params.put("title", title);
        if (birthdate != null) params.put("birthdate", birthdate);
        if (email != null) params.put("email", email);
        if (tags != null) params.put("tags", tags);
        return executePost("group", "add", params, new TypeReference<>() {});
    }

    @Override
    public List<ContactResult> addContact(int groupId, String number, String title, String birthdate, String email, String tags) { return joinUnwrapped(addContactAsync(groupId, number, title, birthdate, email, tags)); }

    @Override
    public CompletableFuture<List<GroupResult>> addGroupAsync(String name, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        if (tag != null) params.put("tag", tag);
        return executePost("group", "addgroup", params, new TypeReference<>() {});
    }

    @Override
    public List<GroupResult> addGroup(String name, String tag) { return joinUnwrapped(addGroupAsync(name, tag)); }

    @Override
    public CompletableFuture<List<ContactResult>> removeContactAsync(String contactId, String mobile, Integer groupId) {
        Map<String, String> params = new HashMap<>();
        if (contactId != null) params.put("contactid", contactId);
        if (mobile != null) params.put("mobile", mobile);
        if (groupId != null) params.put("groupid", String.valueOf(groupId));
        return executeDelete("contact", "remove", params, new TypeReference<>() {});
    }

    @Override
    public List<ContactResult> removeContact(String contactId, String mobile, Integer groupId) { return joinUnwrapped(removeContactAsync(contactId, mobile, groupId)); }

    @Override
    public CompletableFuture<List<GroupResult>> removeGroupAsync(int groupId) {
        Map<String, String> params = Map.of("groupid", String.valueOf(groupId));
        return executeDelete("group", "removegroup", params, new TypeReference<>() {});
    }

    @Override
    public List<GroupResult> removeGroup(int groupId) { return joinUnwrapped(removeGroupAsync(groupId)); }

    @Override
    public CompletableFuture<List<GroupResult>> listGroupsAsync() {
        return executeGet("group", "listofgroups", Collections.emptyMap(), new TypeReference<>() {});
    }

    @Override
    public List<GroupResult> listGroups() { return joinUnwrapped(listGroupsAsync()); }

    @Override
    public CompletableFuture<List<GroupResult>> editGroupAsync(int groupId, String groupName, Byte status, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("groupid", String.valueOf(groupId));
        if (groupName != null) params.put("groupname", groupName);
        if (status != null) {
            params.put("status", String.valueOf(status));
        } else {
            params.put("status", "1");
        }
        if (tag != null) params.put("tag", tag);
        return executePost("group", "editgroup", params, new TypeReference<>() {});
    }

    @Override
    public List<GroupResult> editGroup(int groupId, String groupName, Byte status, String tag) { return joinUnwrapped(editGroupAsync(groupId, groupName, status, tag)); }

    @Override
    public CompletableFuture<List<GroupResult>> searchGroupsAsync(String tag) {
        Map<String, String> params = Map.of("tag", tag);
        return executeGet("group", "search", params, new TypeReference<>() {});
    }

    @Override
    public List<GroupResult> searchGroups(String tag) { return joinUnwrapped(searchGroupsAsync(tag)); }

    // --- Utility ---

    @Override
    public CompletableFuture<ServerDateResult> getServerDateAsync() {
        return executeGet("utils", "getdate", Collections.emptyMap(), new TypeReference<>() {});
    }

    @Override
    public ServerDateResult getServerDate() { return joinUnwrapped(getServerDateAsync()); }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PingResult {
        @JsonProperty("status")
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @Override
    public CompletableFuture<String> pingAsync() {
        return executeGet("utils", "ping", Collections.emptyMap(), new TypeReference<ApiEnvelope<PingResult>>() {})
                .thenApply(res -> res != null ? res.getStatus() : null);
    }

    @Override
    public String ping() { return joinUnwrapped(pingAsync()); }
}
