package com.hopoong.dspmigration.app.legacy.api.keyword.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyKeyword;

import java.time.LocalDateTime;

public class LegacyKeywordCreatedEvent extends LegacyKeywordEvent {

    public LegacyKeywordCreatedEvent(LegacyKeyword legacyKeyword) {
        super(legacyKeyword);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyKeyword.getCreatedAt();
    }

}