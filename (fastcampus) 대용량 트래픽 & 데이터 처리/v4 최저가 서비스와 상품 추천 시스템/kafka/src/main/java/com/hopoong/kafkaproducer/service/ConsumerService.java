package com.hopoong.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsumerService {

    @KafkaListener(topics = "click-logs", groupId = "spring")
    public void consumer (String message) {
        System.out.println("Subscribed :  %s".formatted(message));
    }



}
