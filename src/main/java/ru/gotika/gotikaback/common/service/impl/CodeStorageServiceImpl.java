package ru.gotika.gotikaback.common.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.common.service.CodeStorageService;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CodeStorageServiceImpl implements CodeStorageService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void saveCode(String email, String code) {
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES); // Срок жизни - 5 минут
    }

    @Override
    public String getCode(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    @Override
    public void deleteCode(String email) {
        redisTemplate.delete(email);
    }
}
