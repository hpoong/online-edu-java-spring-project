package com.hopoong.dspmigration.app.legacy.api.user.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;

import java.time.LocalDateTime;

public class LegacyUserDeletedEvent extends LegacyUserEvent {

    public LegacyUserDeletedEvent(LegacyUser legacyUser) {
        super(legacyUser);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyUser.getDeletedAt();
    }

}