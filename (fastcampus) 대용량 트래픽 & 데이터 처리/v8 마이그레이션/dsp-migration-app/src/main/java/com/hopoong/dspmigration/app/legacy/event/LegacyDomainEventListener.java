package com.hopoong.dspmigration.app.legacy.event;


import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class LegacyDomainEventListener {

    @TransactionalEventListener
    public void handleEvent(DomainEvent event) {

    }
}
