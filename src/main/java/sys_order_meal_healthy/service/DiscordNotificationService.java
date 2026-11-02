package sys_order_meal_healthy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscordNotificationService {

    private final WebClient webClient;

    @Value("${discord.webhook.url}")
    private String webhookUrl;

    public Mono<Void> sendOrderNotification(
            String orderId,
            String customerName,
            String phoneNumber,
            String mealPackage,
            Integer totalAmount,
            String proofUrlOrMethod
    ) {

        Map<String, Object> embed = new java.util.HashMap<>(Map.of(
                "title", "\uD83D\uDD14 ĐƠN HÀNG MỚI",
                "description",
                """
                        sMã đơn: %s
                        Khách: %s
                        SĐT: %s
                        Gói đặt: %s
                        Tổng tiền: %s VNĐ
                        Ảnh CK: %s
                        """.formatted(
                        orderId,
                        customerName,
                        phoneNumber,
                        mealPackage,
                        totalAmount,
                        proofUrlOrMethod
                ),
                "color", 0x2EAD68,
                "footer", Map.of(
                        "text", "Gác Bếp Healthy • Cảm ơn bạn đã đặt hàng ❤️"
                ),
                "timestamp", Instant.now().toString()
        ));

        // Hiển thị ảnh trực tiếp trong Discord
        if (proofUrlOrMethod != null
                && proofUrlOrMethod.startsWith("https://")) {

            embed.put("image", Map.of(
                    "url", proofUrlOrMethod
            ));
        }

        Map<String, Object> payload = Map.of(
                "embeds", List.of(embed),
                "allowed_mentions", Map.of(
                        "parse", List.of()
                )
        );

        return webClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(response ->
                        log.info("Discord notification sent successfully")
                )
                .doOnError(error ->
                        log.error("Failed to send Discord notification: {}",
                                error.getMessage())
                )
                .then();
    }
}