package com.hopoong.flow.api.controller;


import com.hopoong.flow.api.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class WaitngRoomContoller {

    private final UserQueueService userQueueService;

    @GetMapping("/watiting-room")
    Mono<Rendering> watitingRoomPage(@RequestParam(name = "queue", defaultValue = "default") String queue,
                                     @RequestParam(name = "userId") Long userId,
                                     @RequestParam(name = "redirectUrl") String redirectUrl,
                                     ServerWebExchange serverWebExchange
    ) {

        var key = "user-queue-%s-token".formatted(queue);
        var cookieValue = serverWebExchange.getRequest().getCookies().getFirst(key);
        var token = cookieValue != null ? cookieValue.getValue() : "";

        // 1. 허용하는지 여부 확인
        // true : redirectUrl
        // 2. false 인경우
        // 몇번째 순번인지 체크 및
        return userQueueService.isAllowedByToken(queue, userId, token)
                .filter(allowed -> allowed)
                .flatMap(allowed -> Mono.just(Rendering.redirectTo(redirectUrl).build()))
                .switchIfEmpty(
                    userQueueService.registerWaitQueue(queue, userId)
                        .onErrorResume(ex -> userQueueService.getRank(queue, userId))
                        .map(
                                rank -> Rendering.view("waiting-room.html")
                                        .modelAttribute("number", rank)
                                        .modelAttribute("userId", userId)
                                        .modelAttribute("queue", queue)
                                        .build()
                        )
                );
    }




}
