package ru.gotika.gotikaback.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class YookassaConfig {

    @Value("${yookassa.shop-id}")
    private String shopId;

    @Value("${yookassa.secret-key}")
    private String secretKey;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .basicAuthentication(shopId, secretKey)
                .build();
    }
}
