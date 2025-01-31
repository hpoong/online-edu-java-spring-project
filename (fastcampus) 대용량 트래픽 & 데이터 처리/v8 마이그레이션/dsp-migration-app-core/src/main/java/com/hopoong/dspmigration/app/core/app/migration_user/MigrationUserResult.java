package com.hopoong.dspmigration.app.core.app.migration_user;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;
import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUserStatus;

import java.time.LocalDateTime;

public record MigrationUserResult(Long id, MigrationUserStatus status, LocalDateTime agreedDate,
                                  LocalDateTime updateDate) {

    public static MigrationUserResult from(MigrationUser migrationUser) {
        return new MigrationUserResult(migrationUser.getId(), migrationUser.getStatus(),
                migrationUser.getAgreedAt(), migrationUser.getUpdateAt());
    }
}
