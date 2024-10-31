package com.hopoong.kafkaproducer.service;

import com.hopoong.kafkaproducer.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final String topicName = "click-logs";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaConfig myConfig;


    // kafka 컨테이너에서 아래 명령어 확인 
    // kafka-console-consumer --bootstrap-server localhost:9092 --topic click-logs --from-beginning
    // kafka-console-consumer --bootstrap-server localhost:9092 --topic userClass --from-beginning
    // kafka-console-producer --broker-list localhost:9092 --topic click-logs
    public void pub(String msg) {
        kafkaTemplate.send(topicName, msg);
    }

}
