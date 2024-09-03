package com.hopoong.flow.api.controller;


import com.hopoong.flow.api.model.AllowUserResponse;
import com.hopoong.flow.api.model.AllowedUserResponse;
import com.hopoong.flow.api.model.RankNumberResponse;
import com.hopoong.flow.api.model.RegisterUserResponse;
import com.hopoong.flow.api.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class UserQueueController {

    private final UserQueueService userQueueService;

    /*
     * 대기열 등록
     */
    @PostMapping("")
    public Mono<RegisterUserResponse> registerUser(
            @RequestParam(name="queue", defaultValue = "default") String queue,
            @RequestParam(name="userId") Long userId) {
        return userQueueService.registerWaitQueue(queue, userId).map(RegisterUserResponse::new);
    }


    /*
     * N개 만큼 진입을 허용
     */
    @PostMapping("/allow")
    public Mono<AllowUserResponse> allowUser(
            @RequestParam(name="queue", defaultValue = "default") String queue,
            @RequestParam(name="count") Long count) {
        return userQueueService.allowUser(queue, count)
                .map(value -> new AllowUserResponse(count, value));
    }


    /*
     * 진입이 가능한 상태인지 조회
     */
    @GetMapping("/allowed")
    public Mono<AllowedUserResponse> isAllowed(
            @RequestParam(name="queue", defaultValue = "default") String queue,
            @RequestParam(name="userId") Long userId) {
        return userQueueService.isAllowed(queue, userId).map(AllowedUserResponse::new);
    }


    /*
     * 진입이 가능한 상태인지 조회
     */
    @GetMapping("/rank")
    public Mono<RankNumberResponse> getRankUser(
            @RequestParam(name="queue", defaultValue = "default") String queue,
            @RequestParam(name="userId") Long userId) {
        return userQueueService.getRank(queue, userId).map(RankNumberResponse::new);
    }


    /*
     * 대기열 이탈을 위한 token 발급
     */
    @GetMapping("/touch")
    Mono<?> touch(@RequestParam(name = "queue", defaultValue = "default") String queue,
                  @RequestParam(name = "userId") Long userId,
                  ServerWebExchange exchange)
    {
        return Mono.defer(() -> userQueueService.generateToken(queue, userId))
                .map(token -> {
                    exchange.getResponse().addCookie(
                        ResponseCookie
                            .from("user-queue-%s-token".formatted(queue), token)
                            .maxAge(Duration.ofSeconds(300))
                            .path("/")
                            .build()
                    );
                    return token;
                });
    }

}
