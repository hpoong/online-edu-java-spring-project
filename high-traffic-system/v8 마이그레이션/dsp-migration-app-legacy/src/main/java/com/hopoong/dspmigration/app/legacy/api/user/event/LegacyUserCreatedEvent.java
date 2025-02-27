package com.hopoong.dspmigration.app.legacy.api.user.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;

import java.time.LocalDateTime;

public class LegacyUserCreatedEvent extends LegacyUserEvent {

    public LegacyUserCreatedEvent(LegacyUser legacyUser) {
        super(legacyUser);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyUser.getCreatedAt();
    }

}