package com.hopoong.dspmigration.app.core.domain.migration.user.event;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;

public class MigrationRetriedEvent extends MigrationUserEvent {
    public MigrationRetriedEvent(MigrationUser migrationUser) {
        super(migrationUser);
    }
}
