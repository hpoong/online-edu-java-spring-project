package com.hopoong.dspmigration.app.internal.event;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUserStatus;
import com.hopoong.dspmigration.app.core.domain.migration.user.event.MigrationUserEvent;

public record MigrationUserMessage(Long userId, MigrationUserStatus status) {

    public static MigrationUserMessage from(MigrationUserEvent event) {
        return new MigrationUserMessage(event.getUserId(), event.getStatus());
    }
}