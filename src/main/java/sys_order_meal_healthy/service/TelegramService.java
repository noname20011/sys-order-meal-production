package sys_order_meal_healthy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TelegramService {

    @Value("${telegram.bot-token}")
    private String token;

    @Value("${telegram.group-id}")
    private String groupId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void send(String message) {

        String url =
                "https://api.telegram.org/bot"
                        + token
                        + "/sendMessage";

        Map<String, Object> body = Map.of(
                "chat_id", groupId,
                "text", message
        );

        restTemplate.postForObject(
                url,
                body,
                String.class
        );
    }
}
