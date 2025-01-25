package com.hopoong.dspmigration.app.legacy.api.adgroup.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyAdGroup;

import java.time.LocalDateTime;

public class LegacyAdGroupDeletedEvent extends LegacyAdGroupEvent {

    public LegacyAdGroupDeletedEvent(LegacyAdGroup legacyAdGroup) {
        super(legacyAdGroup);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyAdGroup.getDeletedAt();
    }

}