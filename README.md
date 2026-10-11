<div align="center">

<br/>

# 📱 Kavenegar Java SDK

**A high-performance Java client for SMS, OTP & Voice — built for modern Java 17+.**

<br/>

[![Java](https://img.shields.io/badge/Java-17%20%7C%2021%20%7C%2022-512BD4?style=for-the-badge&logo=openjdk)](https://openjdk.org)

<br/>

[📦 Install](#-installation) · [🚀 Quick Start](#-quick-start) · [🍃 Spring Boot Integration](#-spring-boot--dependency-injection) · [📖 Docs](http://kavenegar.com/rest.html) · [🇮🇷 فارسی](#-راهنما)

<br/>

</div>

---

## ✨ Why Kavenegar SDK?

<table>
<tr>
<td width="50%">

**🔄 Connection Pooling**
Fully reuses HTTP connection pools via `java.net.http.HttpClient` — no socket exhaustion, no anti-patterns.

</td>
<td width="50%">

**⚡ Async / CompletableFuture**
Every method ships with a `*Async` twin returning `CompletableFuture<T>` for non-blocking, high-throughput reactive workloads.

</td>
</tr>
<tr>
<td>

**🍃 Spring Boot Ready**
First-class `KavenegarApi` interface designed to register cleanly as Spring `@Bean` components.

</td>
<td>

**🌐 Broad Compatibility**
Built for modern **Java 17, 21 & 22+** with Jackson 2.17.1 for lightning-fast JSON serialization.

</td>
</tr>
<tr>
<td>

**📚 Clean API Contract**
100% feature parity with Kavenegar REST API v1.0 and .NET SDK, supporting SMS, OTP Verification, Call/TTS, SubClients, and Media.

</td>
<td>

**🔧 Flexible Options Builder**
Configure with `KavenegarClientOptions.builder()` — set custom timeouts, base URLs, or custom Jackson `ObjectMapper`.

</td>
</tr>
</table>

---

## 🖥️ Platform Support

| Platform / Framework | Version | Async |
|---|---|:---:|
| **Java (JDK)** | 17, 21, 22+ | ✅ (`CompletableFuture`) |
| **Spring Boot** | 3.x / 2.x | ✅ |

---

## 📦 Installation

<details open>
<summary><b>🐘 Gradle (JitPack)</b></summary>

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.kavenegar:kavenegar-java:v2.0.0'
}
```

</details>

<details>
<summary><b>📦 Maven (JitPack)</b></summary>

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.kavenegar</groupId>
    <artifactId>kavenegar-java</artifactId>
    <version>v2.0.0</version>
</dependency>
```

</details>

---

## 🚀 Quick Start

### Synchronous

```java
import com.kavenegar.sdk.KavenegarClient;
import com.kavenegar.sdk.models.SendResult;
import com.kavenegar.sdk.excepctions.KavenegarApiException;
import com.kavenegar.sdk.excepctions.KavenegarHttpException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        KavenegarClient api = new KavenegarClient("YOUR_API_KEY");

        try {
            // ── Single recipient ──────────────────────────────────────────────────
            SendResult result = api.send("10004346", "09120000000", "Hello from Kavenegar Java!");
            System.out.println("✅ Sent | ID: " + result.getMessageId() + " | Status: " + result.getStatus());

            // ── Multiple recipients ───────────────────────────────────────────────
            List<String> receptors = List.of("09120000000", "09120000001");
            List<SendResult> results = api.send("10004346", receptors, "Bulk SMS via Kavenegar Java!");
            for (SendResult r : results) {
                System.out.println("✅ Sent | ID: " + r.getMessageId() + " | Receptor: " + r.getReceptor());
            }
        } catch (KavenegarApiException ex) {
            // API status error (e.g. invalid key, low credit)
            System.err.println("❌ API Error [" + ex.getCode() + "]: " + ex.getMessage());
        } catch (KavenegarHttpException ex) {
            // HTTP connectivity error
            System.err.println("❌ HTTP Error [" + ex.getStatusCode() + "]: " + ex.getMessage());
        }
    }
}
```

---

### ⚡ Async — Non-Blocking `CompletableFuture`

```java
import com.kavenegar.sdk.KavenegarClient;
import com.kavenegar.sdk.models.SendResult;
import com.kavenegar.sdk.excepctions.KavenegarApiException;
import com.kavenegar.sdk.excepctions.KavenegarHttpException;

import java.util.List;

public class MainAsync {
    public static void main(String[] args) {
        KavenegarClient api = new KavenegarClient("YOUR_API_KEY");

        // ── Single recipient (async) ──────────────────────────────────────────
        api.sendAsync("10004346", "09120000000", "Hello async!")
            .thenAccept(result -> {
                System.out.println("✅ Sent | ID: " + result.getMessageId() + " | Cost: " + result.getCost());
            })
            .exceptionally(ex -> {
                if (ex.getCause() instanceof KavenegarApiException apiEx) {
                    System.err.println("❌ API Error [" + apiEx.getCode() + "]: " + apiEx.getMessage());
                } else if (ex.getCause() instanceof KavenegarHttpException httpEx) {
                    System.err.println("❌ HTTP Error [" + httpEx.getStatusCode() + "]: " + httpEx.getMessage());
                }
                return null;
            });
    }
}
```

---

## 🍃 Spring Boot — Dependency Injection

Register once as a `@Bean`, inject everywhere into your Spring `@Service` or `@RestController`.

**`application.yml`**
```yaml
kavenegar:
  api-key: "YOUR_API_KEY"
```

**`KavenegarConfig.java`**
```java
import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.KavenegarClient;
import com.kavenegar.sdk.KavenegarClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KavenegarConfig {

    @Bean
    public KavenegarApi kavenegarApi(@Value("${kavenegar.api-key}") String apiKey) {
        KavenegarClientOptions options = KavenegarClientOptions.builder()
                .apiKey(apiKey)
                .build();
        return new KavenegarClient(options);
    }
}
```

**Usage in any Spring Service:**
```java
import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.models.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class SmsService {

    private final KavenegarApi sms;

    public SmsService(KavenegarApi sms) {
        this.sms = sms;
    }

    public CompletableFuture<SendResult> sendOtpAsync(String phone, String code) {
        return sms.verifyLookupAsync(phone, code, "otp-template");
    }
}
```

---

## ⚙️ Options-Based Configuration

Configure with `KavenegarClientOptions` — ideal for custom HTTP timeout, base URL override, or custom Jackson `ObjectMapper`:

```java
import com.kavenegar.sdk.KavenegarClient;
import com.kavenegar.sdk.KavenegarClientOptions;
import com.kavenegar.sdk.models.SendResult;

import java.time.Duration;

public class CustomOptionsExample {
    public static void main(String[] args) {
        KavenegarClientOptions options = KavenegarClientOptions.builder()
                .apiKey("YOUR_API_KEY")
                .baseUrl("https://api.kavenegar.com") // optional - default is https://api.kavenegar.com
                .timeout(Duration.ofSeconds(15))     // custom HTTP timeout
                .build();

        KavenegarClient api = new KavenegarClient(options);
        SendResult result = api.send("10004346", "09120000000", "Hello!");
        System.out.println("Message ID: " + result.getMessageId());
    }
}
```

---

## 🤝 Contributing

Contributions, bug reports, and feature ideas are always welcome!

| | |
|---|---|
| 🐛 **Bug?** | Open an [issue](../../issues) |
| 💡 **Idea?** | Submit a [pull request](../../pulls) |
| 📧 **Help?** | [support@kavenegar.com](mailto:support@kavenegar.com?Subject=SDK) |

---

<br/>

<div dir="rtl">

## 🇮🇷 راهنما

### معرفی سرویس کاوه نگار

کاوه نگار یک وب سرویس ارسال و دریافت پیامک و تماس صوتی است که به راحتی میتوانید از آن استفاده نمایید.

### ساخت حساب کاربری

اگر در وب سرویس کاوه نگار عضو نیستید میتوانید از [لینک عضویت](http://panel.kavenegar.com/client/membership/register) ثبت نام و اکانت آزمایشی برای تست API دریافت نمایید.

### مستندات

برای مشاهده اطلاعات کامل مستندات [وب سرویس پیامک](http://kavenegar.com/وب-سرویس-پیامک.html) به صفحه [مستندات وب سرویس](http://kavenegar.com/rest.html) مراجعه نمایید.

### راهنمای فارسی

در صورتی که مایل هستید راهنمای فارسی کیت توسعه کاوه نگار را مطالعه کنید به صفحه [کد ارسال پیامک](http://kavenegar.com/sdk.html) مراجعه نمایید.

### اطلاعات بیشتر

برای مطالعه بیشتر به صفحه معرفی [وب سرویس اس ام اس](http://kavenegar.com) کاوه نگار مراجعه نمایید.

اگر در استفاده از کیت‌های سرویس کاوه نگار مشکلی یا پیشنهادی داشتید ما را با یک Pull Request یا ارسال ایمیل به [support@kavenegar.com](mailto:support@kavenegar.com) خوشحال کنید.

---

[http://kavenegar.com](http://kavenegar.com)

</div>
