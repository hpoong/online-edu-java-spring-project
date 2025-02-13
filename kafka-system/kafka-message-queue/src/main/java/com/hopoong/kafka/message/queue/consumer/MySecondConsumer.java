package com.hopoong.kafka.message.queue.consumer;


import com.hopoong.kafka.message.queue.model.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class MySecondConsumer {

    @KafkaListener(
            topics = { Topic.MY_SECOND_TOPIC },
            groupId = "test-consumer-group",
            containerFactory = "secondKafkaListenerContainerFactory"
    )
    public void accept(ConsumerRecord<String, String> message) {
        System.out.println("[Second Consumer] Message arrived! - " + message.value());
        System.out.println("[Second Consumer] Offset - " + message.offset() + " / Partition - " + message.partition());
    }
}
