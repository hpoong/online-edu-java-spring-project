package com.hopoong.dspmigration.app.batch.message;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUserStatus;

public record MigrationUserMessage(Long userId, MigrationUserStatus status,
                                   MigrationUserStatus prevStatus) {
}