package com.hopoong.dspmigration.app.legacy.event;

import java.time.LocalDateTime;

public interface DomainEvent {

    AggregateType aggregateType();

    Long aggregateId();

    LocalDateTime occurredOn();

    Long ownerId();
}