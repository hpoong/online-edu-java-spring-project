package com.hopoong.dspmigration.app.legacy.event;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.cloud.stream.function.StreamBridge;


@Component
@RequiredArgsConstructor
public class LegacyDomainEventListener {

    private final StreamBridge streamBridge;

    private static final String OUTPUT_BINDING = "legacy-rabbit-out";

    @TransactionalEventListener
    public void handleEvent(DomainEvent event) {
        streamBridge.send(OUTPUT_BINDING, LegacyDomainMessage.from(event));
    }

}
