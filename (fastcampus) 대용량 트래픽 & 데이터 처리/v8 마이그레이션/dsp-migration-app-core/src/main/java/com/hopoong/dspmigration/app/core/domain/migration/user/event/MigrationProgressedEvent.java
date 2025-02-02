package com.hopoong.dspmigration.app.core.domain.migration.user.event;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;

public class MigrationProgressedEvent extends MigrationUserEvent {

    public MigrationProgressedEvent(MigrationUser migrationUser) {
        super(migrationUser);
    }
}
