package com.kavenegar.sdk;

import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import com.kavenegar.sdk.excepctions.KavenegarApiException;
import com.kavenegar.sdk.excepctions.KavenegarHttpException;
import com.kavenegar.sdk.models.AccountInfoResult;
import com.kavenegar.sdk.models.MediaResult;
import com.kavenegar.sdk.models.SendArrayRequest;
import com.kavenegar.sdk.models.SendResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class KavenegarServiceTest {

    private HttpClient httpClient;
    private HttpResponse<String> response;
    private KavenegarClient service;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        httpClient = mock(HttpClient.class);
        response = mock(HttpResponse.class);
        service = new KavenegarClient(KavenegarClientOptions.builder()
                .apiKey("test-api-key")
                .baseUrl("https://example.test/")
                .httpClient(httpClient)
                .build());
    }

    @Test
    void sendAsyncBuildsEncodedPostAndMapsResponse() {
        respond(200, """
                {
                  "return": {"status": 200, "message": "OK"},
                  "entries": [{
                    "messageid": 42,
                    "message": "hello world",
                    "status": 1,
                    "statustext": "queued",
                    "sender": "1000",
                    "receptor": "09120000000",
                    "date": 1700000000,
                    "cost": 120
                  }]
                }
                """);

        List<SendResult> results = service
                .sendAsync("1000", List.of("09120000000"), "hello world")
                .join();

        assertEquals(1, results.size());
        assertEquals(42L, results.get(0).getMessageId());
        assertEquals("queued", results.get(0).getStatusText());

        HttpRequest request = capturedRequest();
        assertEquals("POST", request.method());
        assertEquals(
                "https://example.test/v1/test-api-key/sms/send.json",
                request.uri().toString());
        String body = requestBody(request);
        assertTrue(body.contains("sender=1000"));
        assertTrue(body.contains("receptor=09120000000"));
        assertTrue(body.contains("message=hello+world"));
    }

    @Test
    void accountInfoUsesGetEndpointAndMapsResponse() {
        respond(200, """
                {
                  "return": {"status": 200, "message": "OK"},
                  "entries": {
                    "remaincredit": 7500,
                    "expiredate": 1800000000,
                    "type": "corporate"
                  }
                }
                """);

        AccountInfoResult result = service.getAccountInfo();

        assertEquals(7500L, result.getRemainCredit());
        assertEquals("corporate", result.getType());

        HttpRequest request = capturedRequest();
        assertEquals("GET", request.method());
        assertEquals(
                "https://example.test/v1/test-api-key/account/info.json",
                request.uri().toString());
    }

    @Test
    void synchronousServiceMethodUnwrapsApiException() {
        respond(200, """
                {
                  "return": {"status": 101, "message": "Invalid API key"},
                  "entries": null
                }
                """);

        KavenegarApiException exception = assertThrows(
                KavenegarApiException.class,
                service::getAccountInfo);

        assertTrue(exception instanceof ApiException);
        assertEquals(101, exception.getRawCode());
        assertEquals("Invalid API key", exception.getMessage());
    }

    @Test
    void asynchronousServiceMethodReportsHttpException() {
        respond(503, "Service unavailable");

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.getServerDateAsync().join());

        assertTrue(exception.getCause() instanceof KavenegarHttpException);
        assertTrue(exception.getCause() instanceof HttpException);
        assertEquals(
                503,
                ((HttpException) exception.getCause()).getCode());
    }

    @Test
    void dotNetAliasesAndMediaReviewAndSendArrayTrimmingWork() {
        respond(200, """
                {
                  "result": {"status": 200, "message": "OK"},
                  "entries": {
                    "id": "11111111-1111-1111-1111-111111111111",
                    "name": "file.jpg",
                    "status": 1,
                    "status_desc": "Uploaded",
                    "review": {
                      "status": 1,
                      "status_desc": "Approved",
                      "reason": 0,
                      "reason_desc": "None"
                    }
                  }
                }
                """);

        IKavenegarApi dotNetApi = service;
        MediaResult media = dotNetApi.getMedia("11111111-1111-1111-1111-111111111111", null);
        assertNotNull(media.getReview());
        assertEquals("Approved", media.getReview().getStatusDesc());

        respond(200, """
                {
                  "return": {"status": 200, "message": "OK"},
                  "entries": []
                }
                """);
        dotNetApi.sendArray(SendArrayRequest.builder()
                .senders(List.of(" 1000 "))
                .receptors(List.of(" 09120000000 "))
                .messages(List.of("msg"))
                .build());
    }

    @Test
    void constructorRejectsBlankApiKey() {
        assertThrows(IllegalArgumentException.class, () -> new KavenegarClient("  "));
    }

    private void respond(int statusCode, String body) {
        when(response.statusCode()).thenReturn(statusCode);
        when(response.body()).thenReturn(body);
        when(httpClient.sendAsync(
                any(HttpRequest.class),
                org.mockito.ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(CompletableFuture.completedFuture(response));
    }

    private HttpRequest capturedRequest() {
        ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClient).sendAsync(
                requestCaptor.capture(),
                org.mockito.ArgumentMatchers.<HttpResponse.BodyHandler<String>>any());
        return requestCaptor.getValue();
    }

    private static String requestBody(HttpRequest request) {
        HttpRequest.BodyPublisher publisher = request.bodyPublisher().orElseThrow();
        StringBuilder body = new StringBuilder();
        CompletableFuture<Void> completed = new CompletableFuture<>();

        publisher.subscribe(new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(ByteBuffer item) {
                byte[] bytes = new byte[item.remaining()];
                item.get(bytes);
                body.append(new String(bytes, java.nio.charset.StandardCharsets.UTF_8));
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                completed.completeExceptionally(throwable);
            }

            @Override
            public void onComplete() {
                completed.complete(null);
            }
        });

        completed.join();
        return body.toString();
    }
}
