package com.hopoong.couponcore.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@RequiredArgsConstructor
public class RedisLockExecutor {

    private final RedissonClient redissonClient;

    /*
     * redis lock 처리
     */
    public void execute(String lockName, long waitMilliSecond, long leaseMilliSecond, Runnable logic) {
        RLock lock = redissonClient.getLock(lockName);
        try {
            boolean isLock = lock.tryLock(waitMilliSecond, leaseMilliSecond, TimeUnit.MILLISECONDS); // lock 획득처리

            if(!isLock) {
                throw new IllegalStateException("[" + lockName + "] lock 획득 실패");
            }

            logic.run();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
