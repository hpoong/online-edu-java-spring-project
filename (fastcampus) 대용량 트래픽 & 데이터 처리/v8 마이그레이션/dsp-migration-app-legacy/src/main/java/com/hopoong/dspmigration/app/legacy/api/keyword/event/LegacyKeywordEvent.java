package com.hopoong.dspmigration.app.legacy.api.keyword.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyKeyword;
import com.hopoong.dspmigration.app.legacy.event.AggregateType;
import com.hopoong.dspmigration.app.legacy.event.DomainEvent;

import java.time.LocalDateTime;

public abstract class LegacyKeywordEvent implements DomainEvent {

    protected LegacyKeyword legacyKeyword;

    public LegacyKeywordEvent(LegacyKeyword legacyKeyword) {
        this.legacyKeyword = legacyKeyword;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.KEYWORD;
    }

    @Override
    public Long aggregateId() {
        return legacyKeyword.getId();
    }

    @Override
    public Long ownerId() {
        return legacyKeyword.getUserId();
    }

    @Override
    public abstract LocalDateTime occurredOn();

}