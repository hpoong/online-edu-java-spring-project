package com.hopoong.dspmigration.app.legacy.api.campaign;

import com.hopoong.dspmigration.app.legacy.event.AggregateType;
import com.hopoong.dspmigration.app.legacy.event.DomainEvent;

import java.time.LocalDateTime;

public class LegacyCampaignEvent implements DomainEvent {
    @Override
    public AggregateType aggregateType() {
        return null;
    }

    @Override
    public Long aggregateId() {
        return 0L;
    }

    @Override
    public LocalDateTime occurredOn() {
        return null;
    }

    @Override
    public Long ownerId() {
        return 0L;
    }
}
