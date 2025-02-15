package com.hopoong.kafka.message.queue.consumer;

import com.hopoong.kafka.message.queue.model.MyMessage;
import com.hopoong.kafka.message.queue.model.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {

    @KafkaListener(
            topics = { Topic.MY_JSON_TOPIC },
            groupId = "test-consumer-group"
    )
    public void accept(ConsumerRecord<String, MyMessage> message, Acknowledgment acknowledgment) {
        System.out.println("[MyConsumer] ::::: " + message.value());
        acknowledgment.acknowledge();
    }
}