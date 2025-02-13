package com.hopoong.kafka.message.queue.producer;

import com.hopoong.kafka.message.queue.model.MyMessage;
import com.hopoong.kafka.message.queue.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyProducer {

    private final KafkaTemplate<String, MyMessage> kafkaTemplate;

    public void sendMessage(MyMessage myMessage) {
        kafkaTemplate.send(Topic.MY_JSON_TOPIC, String.valueOf(myMessage.getAge()), myMessage);
    }
}