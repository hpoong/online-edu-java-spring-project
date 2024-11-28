package com.hopoong.project.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaService kafkaService;

    @GetMapping("/test")
    public void test(@RequestParam String msg) {
        kafkaService.publish(msg);
    }


}
