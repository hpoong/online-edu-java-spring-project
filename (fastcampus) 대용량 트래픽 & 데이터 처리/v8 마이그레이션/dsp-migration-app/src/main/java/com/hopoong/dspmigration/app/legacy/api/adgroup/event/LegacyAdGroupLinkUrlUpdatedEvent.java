package com.hopoong.dspmigration.app.legacy.api.adgroup.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyAdGroup;
import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;

import java.time.LocalDateTime;

public class LegacyAdGroupLinkUrlUpdatedEvent extends LegacyAdGroupEvent {

    public LegacyAdGroupLinkUrlUpdatedEvent(LegacyAdGroup legacyAdGroup) {
        super(legacyAdGroup);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyAdGroup.getUpdatedAt();
    }

}