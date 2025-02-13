package com.hopoong.kafka.message.queue.producer;

import com.hopoong.kafka.message.queue.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MySecondProducer {

    @Qualifier("secondKafkaTemplate")
    private final KafkaTemplate<String, String> secondKafkaTemplate;

    public void sendMessageWithKey(String key, String message) {
        secondKafkaTemplate.send(Topic.MY_SECOND_TOPIC, key, message);
    }
}