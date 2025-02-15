package com.hopoong.kafka.message.queue.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopoong.kafka.message.queue.common.CustomObjectMapper;
import com.hopoong.kafka.message.queue.model.MyCdcMessage;
import com.hopoong.kafka.message.queue.model.Topic;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class MyCdcConsumer {

    private final CustomObjectMapper objectMapper;

    @KafkaListener(
            topics = {Topic.MY_CDC_TOPIC },
            groupId = "cdc-consumer-group",
            concurrency = "1"
    )
    public void listen(ConsumerRecord<String, String> message, Acknowledgment acknowledgment) throws JsonProcessingException {
        MyCdcMessage myCdcMessage = objectMapper.readValue(message.value(), MyCdcMessage.class);
        System.out.println("[Cdc Consumer] " + myCdcMessage.getOperationType() + " Message arrived! (id: " + myCdcMessage.getId() + ") - " + myCdcMessage.getPayload());
        acknowledgment.acknowledge();
    }
}