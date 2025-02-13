package com.hopoong.kafka.message.queue.consumer;

import com.hopoong.kafka.message.queue.model.MyMessage;
import com.hopoong.kafka.message.queue.model.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MyThirdConsumer {

    @KafkaListener(
            topics = {Topic.MY_JSON_TOPIC },
            groupId = "batch-test-consumer-group", // MyConsumer의 groupId와 반드시 달라야 함
            containerFactory = "batchKafkaListenerContainerFactory"
    )
    public void accept(List<ConsumerRecord<String, MyMessage>> messages) {
        System.out.println("[Third Consumer] Batch message arrived! - count " + messages.size());
        messages.forEach(message -> {
                    System.out.println("ㄴ [Third Consumer] Value :: " + message.value() + " / Offset :: " + message.offset() + " / Partition :: " + message.partition());
                }
        );
    }
}