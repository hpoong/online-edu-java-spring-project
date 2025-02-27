package com.hopoong.dspmigration.app.legacy.api.adgroup.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyAdGroup;

import java.time.LocalDateTime;

public class LegacyAdGroupCreatedEvent extends LegacyAdGroupEvent {

    public LegacyAdGroupCreatedEvent(LegacyAdGroup legacyAdGroup) {
        super(legacyAdGroup);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyAdGroup.getCreatedAt();
    }

}