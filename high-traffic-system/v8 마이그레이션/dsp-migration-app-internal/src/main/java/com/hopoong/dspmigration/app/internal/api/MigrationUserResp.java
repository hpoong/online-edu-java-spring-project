package com.hopoong.dspmigration.app.internal.api;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUserStatus;

import java.time.LocalDateTime;

public record MigrationUserResp(Long id, MigrationUserStatus status, LocalDateTime agreedDate,
                                LocalDateTime updateDate) {

}