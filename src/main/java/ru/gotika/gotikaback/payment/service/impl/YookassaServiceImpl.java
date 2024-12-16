package ru.gotika.gotikaback.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gotika.gotikaback.payment.service.YookassaService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class YookassaServiceImpl implements YookassaService {

    @Value("${yookassa.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Override
    public Map<String, Object> createPayment(Double amount, String description, String confirmationUrl, Long orderId, String idempotenceKey) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", Map.of("value", String.format("%.2f", amount).replace(",", "."), "currency", "RUB"));
        requestBody.put("confirmation", Map.of("type", "redirect", "return_url", confirmationUrl));
        requestBody.put("description", description);
        requestBody.put("capture", true);
        requestBody.put("metadata", Map.of("orderId", orderId.toString()));
        requestBody.put("test", true);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Idempotence-Key", idempotenceKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);


        ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Ошибка при создании платежа: " + response.getStatusCode());
        }
    }

    @Override
    public Map<String, Object> getPaymentStatus(String paymentId) {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                apiUrl + "/" + paymentId,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Ошибка при проверке статуса платежа: " + response.getStatusCode());
        }
    }
}
