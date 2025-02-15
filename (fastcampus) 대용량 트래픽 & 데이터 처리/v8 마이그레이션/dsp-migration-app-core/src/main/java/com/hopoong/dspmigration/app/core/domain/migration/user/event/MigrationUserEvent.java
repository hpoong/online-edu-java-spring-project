package com.hopoong.dspmigration.app.core.domain.migration.user.event;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;
import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUserStatus;

public class MigrationUserEvent {

    protected MigrationUser migrationUser;

    public MigrationUserEvent(MigrationUser migrationUser) {
        this.migrationUser = migrationUser;
    }

    public Long getUserId() {
        return migrationUser.getId();
    }

    public MigrationUserStatus getStatus() {
        return migrationUser.getStatus();
    }

    public MigrationUserStatus getPrevStatus() {
        return migrationUser.getPrevStatus();
    }
}
