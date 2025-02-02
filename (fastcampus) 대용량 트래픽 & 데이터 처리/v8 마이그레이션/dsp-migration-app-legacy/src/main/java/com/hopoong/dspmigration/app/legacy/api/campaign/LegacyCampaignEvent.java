package com.hopoong.dspmigration.app.legacy.api.campaign;

import com.hopoong.dspmigration.app.legacy.domain.LegacyCampaign;
import com.hopoong.dspmigration.app.legacy.event.AggregateType;
import com.hopoong.dspmigration.app.legacy.event.DomainEvent;

import java.time.LocalDateTime;

public abstract class LegacyCampaignEvent implements DomainEvent {

    protected LegacyCampaign legacyCampaign;

    public LegacyCampaignEvent(LegacyCampaign legacyCampaign) {
        this.legacyCampaign = legacyCampaign;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.CAMPAIGN;
    }

    @Override
    public Long aggregateId() {
        return legacyCampaign.getId();
    }

    @Override
    public Long ownerId() {
        return legacyCampaign.getUserId();
    }

    @Override
    public abstract LocalDateTime occurredOn();

}
