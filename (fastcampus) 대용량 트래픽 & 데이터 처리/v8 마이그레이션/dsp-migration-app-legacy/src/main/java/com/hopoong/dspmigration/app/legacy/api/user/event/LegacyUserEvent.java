package com.hopoong.dspmigration.app.legacy.api.user.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;
import com.hopoong.dspmigration.app.legacy.event.AggregateType;
import com.hopoong.dspmigration.app.legacy.event.DomainEvent;

import java.time.LocalDateTime;

public abstract class LegacyUserEvent implements DomainEvent {

    protected LegacyUser legacyUser;

    public LegacyUserEvent(LegacyUser legacyUser) {
        this.legacyUser = legacyUser;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.USER;
    }

    @Override
    public Long aggregateId() {
        return legacyUser.getId();
    }

    @Override
    public abstract LocalDateTime occurredOn();

    @Override
    public Long ownerId() {
        return legacyUser.getId();
    }

}
