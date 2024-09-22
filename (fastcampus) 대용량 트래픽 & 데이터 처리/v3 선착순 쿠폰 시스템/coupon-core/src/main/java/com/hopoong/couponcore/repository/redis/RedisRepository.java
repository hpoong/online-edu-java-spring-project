package com.hopoong.couponcore.repository.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopoong.couponcore.exception.CouponIssueException;
import com.hopoong.couponcore.exception.ErrorCode;
import com.hopoong.couponcore.repository.redis.dto.CouponIssueRequest;
import com.hopoong.couponcore.util.CouponRedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate redisTemplate;

    private final RedisScript<String> issueScript = this.issueRequestScript();

    private final String issueRequestQueueKey = CouponRedisUtils.getIssueRequestQueueKey();

    private final ObjectMapper  objectMapper = new ObjectMapper();


    public Boolean zadd(String key, String value, double score) {
        return redisTemplate.opsForZSet().addIfAbsent(key, value, score);
    }


    public Long sAdd(String key, String value) {
        return redisTemplate.opsForSet().add(key, value);
    }


    public Long sCard(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    public Boolean sIsMember(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }


    public Long rPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }


    public String lPop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public String lIndex(String key, long index) {
        return (String) redisTemplate.opsForList().index(key, index);
    }


    public void  issueRequest(long couponId, long userId, int totalIssueQuantity) {

        String issueRequestKey = CouponRedisUtils.getIssueRequestKey(couponId);
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(couponId, userId);

        try {

            String code = (String) redisTemplate.execute(
                    issueScript,
                    List.of(issueRequestKey, issueRequestQueueKey),
                    String.valueOf(userId),
                    String.valueOf(totalIssueQuantity),
                    objectMapper.writeValueAsString(couponIssueRequest)
            );

            CouponIssueRequestCode.checkRequestResult(CouponIssueRequestCode.find(code));
        }catch (Exception e) {
            throw new CouponIssueException(ErrorCode.FAIL_COUPON_ISSUE_REQUEST, "input: %s".formatted(couponIssueRequest));
        }

    }

    private RedisScript<String> issueRequestScript() {

        String script = """
                    if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
                        return '2'
                    end
                                   
                    if tonumber(ARGV[2]) > redis.call('SCARD', KEYS[1]) then
                        redis.call('SADD', KEYS[1], ARGV[1])
                        redis.call('RPUSH', KEYS[2], ARGV[3])
                        return '1'
                    end
                                   
                    return '3'
                """;

        return RedisScript.of(script, String.class);
    }
}
