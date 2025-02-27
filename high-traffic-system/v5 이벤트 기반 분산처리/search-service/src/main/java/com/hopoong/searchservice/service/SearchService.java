package com.hopoong.searchservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchService {

    private final StringRedisTemplate stringRedisTemplate;


    public void addTagCache(Long productId, List<String> tags) {
        var ops = stringRedisTemplate.opsForSet();
        tags.forEach(tag -> {
            ops.add(tag, productId.toString());
        });
    }


    public void removeTagCache(Long productId, List<String> tags) {
        var ops = stringRedisTemplate.opsForSet();
        tags.forEach(tag -> {
            ops.remove(tag, productId.toString());
        });
    }


    public List<Long> getProductIdByTag(String tag) {
        var ops = stringRedisTemplate.opsForSet();
        var values = ops.members(tag);
        if(!values.isEmpty()) {
            return values.stream().map(Long::parseLong).toList();
        }
        return Collections.emptyList();
    }
}
