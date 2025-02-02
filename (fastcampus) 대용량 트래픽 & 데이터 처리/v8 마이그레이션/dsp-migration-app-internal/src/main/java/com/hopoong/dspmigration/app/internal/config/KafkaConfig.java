package com.hopoong.dspmigration.app.internal.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "106.245.232.58:39194");
//        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, "migration-admin-client");
//        return new KafkaAdmin(configs);
//    }
}
