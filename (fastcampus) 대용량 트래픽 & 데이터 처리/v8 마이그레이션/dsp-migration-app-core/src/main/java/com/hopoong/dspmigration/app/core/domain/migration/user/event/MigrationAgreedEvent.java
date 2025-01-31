package com.hopoong.dspmigration.app.core.domain.migration.user.event;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;

public class MigrationAgreedEvent extends MigrationUserEvent {

    public MigrationAgreedEvent(MigrationUser migrationUser) {
        super(migrationUser);
    }
}