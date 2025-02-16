package com.hopoong.kafka.message.queue.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.kafka.json")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }


    @Bean
    @Primary
    public ConsumerFactory<String, Object> consumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getValueDeserializer());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // 수동 커밋
        return new DefaultKafkaConsumerFactory<>(props);
    }


    @Bean
    @Primary
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            DefaultErrorHandler kafkaErrorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // 수동 커밋
        factory.setCommonErrorHandler(kafkaErrorHandler); // 재시도 정책 설정
        factory.setConcurrency(1);
        return factory;
    }


    // ** 재시도 정책 설정
    @Bean
    public DefaultErrorHandler kafkaErrorHandler(KafkaTemplate<String, Object> kafkaTemplate) {

        // ✅ 재시도 정책 설정
        ExponentialBackOffWithMaxRetries backOffPolicy = new ExponentialBackOffWithMaxRetries(5);
        backOffPolicy.setInitialInterval(1000);  // 첫 재시도까지 1초 대기
        backOffPolicy.setMultiplier(2.0);       // 재시도 간격 2배 증가
        backOffPolicy.setMaxInterval(15000);    // 최대 15초까지 증가

        // ✅ DLQ 처리 및 로깅 추가 x
//        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(backOffPolicy);

        // ✅ DLQ 처리 및 로깅 추가 o
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(kafkaTemplate, (record, exception) -> {
                    String dlqTopic = "error-topic.DLQ";
                    return new TopicPartition(dlqTopic, record.partition());
                }),
                backOffPolicy
        );

        // ✅ 재시도 횟수 로깅 추가
        defaultErrorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
            System.err.println("❌ 메시지 재시도 [" + deliveryAttempt + "]회 실패: " + record.value());
        });

        // ✅ retry 하지 않는 Exception
        defaultErrorHandler.addNotRetryableExceptions(IllegalArgumentException.class);

        return defaultErrorHandler;
    }


//    // ** Error 발생시 retry 처리 후 stop 처리
//    @Bean
//    @Primary
//    public CommonErrorHandler errorHandler() {
//        CommonContainerStoppingErrorHandler cseh = new CommonContainerStoppingErrorHandler();
//        AtomicReference<Consumer<? ,?>> consumer2 = new AtomicReference<>();
//        AtomicReference<MessageListenerContainer> container2 = new AtomicReference<>();
//
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler((rec, ex) -> {
//            cseh.handleRemaining(ex, Collections.singletonList(rec), consumer2.get(), container2.get());
//        }, generateBackOff()) {
//
//            @Override
//            public void handleRemaining(
//                    Exception thrownException,
//                    List<ConsumerRecord<?, ?>> records,
//                    Consumer<?, ?> consumer,
//                    MessageListenerContainer container
//            ) {
//                consumer2.set(consumer);
//                container2.set(container);
//                super.handleRemaining(thrownException, records, consumer, container);
//            }
//        };
//        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class);
//        return errorHandler;
//    }


    private BackOff generateBackOff() {
        ExponentialBackOff backOff = new ExponentialBackOff(1000, 2);
        backOff.setMaxElapsedTime(10000);
        return backOff;
    }



    @Bean
    @Primary
    public ProducerFactory<String, Object> producerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getValueSerializer());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // 중복 메시지를 방지 :: ask -1처리 :: producer 처리
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @Primary
    public KafkaTemplate<String, ?> kafkaTemplate(KafkaProperties kafkaProperties) {
        return new KafkaTemplate<>(producerFactory(kafkaProperties));
    }


}
