package com.hopoong.dspmigration.app.legacy.api.adgroup.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyAdGroup;
import com.hopoong.dspmigration.app.legacy.event.AggregateType;
import com.hopoong.dspmigration.app.legacy.event.DomainEvent;

import java.time.LocalDateTime;

public abstract class LegacyAdGroupEvent implements DomainEvent {

    protected LegacyAdGroup legacyAdGroup;

    public LegacyAdGroupEvent(LegacyAdGroup legacyAdGroup) {
        this.legacyAdGroup = legacyAdGroup;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.ADGROUP;
    }

    @Override
    public Long aggregateId() {
        return legacyAdGroup.getId();
    }

    @Override
    public Long ownerId() {
        return legacyAdGroup.getUserId();
    }

    @Override
    public abstract LocalDateTime occurredOn();

}
