package com.hopoong.dspmigration.app.legacy.api.user.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;

import java.time.LocalDateTime;

public class LegacyUserNameUpdatedEvent  extends LegacyUserEvent {

    public LegacyUserNameUpdatedEvent(LegacyUser legacyUser) {
        super(legacyUser);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyUser.getUpdatedAt();
    }

}