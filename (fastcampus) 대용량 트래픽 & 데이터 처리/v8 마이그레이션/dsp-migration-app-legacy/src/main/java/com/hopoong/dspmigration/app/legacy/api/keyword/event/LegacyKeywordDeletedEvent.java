package com.hopoong.dspmigration.app.legacy.api.keyword.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyKeyword;
import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;

import java.time.LocalDateTime;

public class LegacyKeywordDeletedEvent extends LegacyKeywordEvent {

    public LegacyKeywordDeletedEvent(LegacyKeyword legacyKeyword) {
        super(legacyKeyword);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyKeyword.getDeletedAt();
    }

}