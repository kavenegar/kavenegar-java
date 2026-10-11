package com.kavenegar.sdk;

import com.kavenegar.sdk.models.MediaDeleteResult;
import com.kavenegar.sdk.models.MediaListResult;
import com.kavenegar.sdk.models.MediaResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class MediaTest {

    private static final String SAMPLE_IMAGE_URL = "https://fastly.picsum.photos/id/237/200/200.jpg?hmac=zHUGikXUDyLCCmvyww1izLK3R3k8oRYBRiTizZEdyfI";

    private String apiKey;
    private KavenegarClient api;

    @BeforeEach
    public void setUp() {
        apiKey = System.getenv().getOrDefault("KAVENEGAR_API_KEY", "<put your apikey here>");
        api = new KavenegarClient(apiKey);
    }

    private byte[] downloadTestImage() {
        try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SAMPLE_IMAGE_URL)).GET().build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() == 200) {
                return response.body();
            }
        } catch (Exception ignored) {}
        return new byte[]{0x00, 0x01, 0x02, 0x03, 0x04};
    }

    @Test
    public void test_ListMedia() {
        MediaListResult res = api.listMedia(1, 10);
        assertNotNull(res);
    }

    @Test
    public void test_ListMedia_Async() {
        MediaListResult res = api.listMediaAsync(1, 10).join();
        assertNotNull(res);
    }

    @Test
    public void test_UploadMedia() {
        byte[] fileBytes = downloadTestImage();
        MediaResult uploaded = api.uploadMedia("test_image.jpg", fileBytes);
        assertNotNull(uploaded);
        if (uploaded.getId() != null) {
            api.deleteMedia(uploaded.getId());
        }
    }

    @Test
    public void test_UploadMedia_Async() {
        byte[] fileBytes = downloadTestImage();
        MediaResult uploaded = api.uploadMediaAsync("test_image.jpg", fileBytes).join();
        assertNotNull(uploaded);
        if (uploaded.getId() != null) {
            api.deleteMediaAsync(uploaded.getId()).join();
        }
    }

    @Test
    public void test_GetMedia() {
        byte[] fileBytes = downloadTestImage();
        MediaResult uploaded = api.uploadMedia("temp_get_image.jpg", fileBytes);
        assertNotNull(uploaded);

        MediaResult details = api.getMedia(uploaded.getId(), null);
        assertNotNull(details);

        if (uploaded.getId() != null) {
            api.deleteMedia(uploaded.getId());
        }
    }

    @Test
    public void test_GetMedia_Async() {
        byte[] fileBytes = downloadTestImage();
        MediaResult uploaded = api.uploadMediaAsync("temp_get_image.jpg", fileBytes).join();
        assertNotNull(uploaded);

        MediaResult details = api.getMediaAsync(uploaded.getId(), null).join();
        assertNotNull(details);

        if (uploaded.getId() != null) {
            api.deleteMediaAsync(uploaded.getId()).join();
        }
    }

    @Test
    public void test_DeleteMedia() {
        byte[] fileBytes = downloadTestImage();
        MediaResult uploaded = api.uploadMedia("temp_delete_image.jpg", fileBytes);
        assertNotNull(uploaded);

        MediaDeleteResult deleteResult = api.deleteMedia(uploaded.getId());
        assertNotNull(deleteResult);
    }

    @Test
    public void test_DeleteMedia_Async() {
        byte[] fileBytes = downloadTestImage();
        MediaResult uploaded = api.uploadMediaAsync("temp_delete_image.jpg", fileBytes).join();
        assertNotNull(uploaded);

        MediaDeleteResult deleteResult = api.deleteMediaAsync(uploaded.getId()).join();
        assertNotNull(deleteResult);
    }
}
