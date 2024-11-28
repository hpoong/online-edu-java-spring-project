package com.hopoong.project.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaService {


    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String msg) {
        kafkaTemplate.send("topic1", "message send ::: %s".formatted(msg));
    }

    @KafkaListener(topics = "topic1", groupId = "test-group")
    public void consume(String message) {
        System.out.println("topic1 consumed :::: " + message);
    }

}
