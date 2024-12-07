package ru.gotika.gotikaback.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CodeStorageService {

    private final StringRedisTemplate redisTemplate;

    public void saveCode(String email, String code) {
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES); // Срок жизни - 5 минут
    }

    public String getCode(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public void deleteCode(String email) {
        redisTemplate.delete(email);
    }
}
