package com.hopoong.dspmigration.app.recent.message;

import com.hopoong.dspmigration.app.core.dispatcher.MigrationDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegacyDomainMessageHandler {

    private final MigrationDispatcher migrationDispatcher;

    @Bean
    public Consumer<LegacyDomainMessage> legacyConsumer() {
        return message -> {
            log.info(message.toString());
            migrationDispatcher.dispatch(message.aggregateId(), message.aggregateType());
        };
    }
}