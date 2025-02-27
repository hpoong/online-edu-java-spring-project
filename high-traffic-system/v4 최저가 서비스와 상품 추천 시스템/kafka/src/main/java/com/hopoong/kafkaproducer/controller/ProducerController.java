package com.hopoong.kafkaproducer.controller;

import com.hopoong.kafkaproducer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping("/message")
    public void PublishMessage(@RequestParam String msg) {
        producerService.pub(msg);
    }

}
