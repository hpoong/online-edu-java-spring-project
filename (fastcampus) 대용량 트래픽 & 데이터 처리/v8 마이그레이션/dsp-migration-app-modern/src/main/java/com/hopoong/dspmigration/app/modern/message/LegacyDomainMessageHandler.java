package com.hopoong.dspmigration.app.modern.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class LegacyDomainMessageHandler {

    @Bean
    public Consumer<LegacyDomainMessage> legacyConsumer() {
        return message -> log.info(message.toString());
    }
}