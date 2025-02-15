package com.hopoong.kafka.message.queue.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopoong.kafka.message.queue.common.CustomObjectMapper;
import com.hopoong.kafka.message.queue.model.MyCdcMessage;
import com.hopoong.kafka.message.queue.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyCdcProducer {

    private final CustomObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(MyCdcMessage message) throws JsonProcessingException {
        kafkaTemplate.send(
                Topic.MY_CDC_TOPIC,
                objectMapper.writeValueAsString(message)
        );
    }
}