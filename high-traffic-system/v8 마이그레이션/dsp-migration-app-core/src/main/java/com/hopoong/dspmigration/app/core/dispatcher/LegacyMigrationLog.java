package com.hopoong.dspmigration.app.core.dispatcher;

import com.hopoong.dspmigration.app.core.domain.AggregateType;

public record LegacyMigrationLog(boolean isSuccess, AggregateType aggregateType, Long aggregateId, Long userId) {

    public static LegacyMigrationLog success(Long userId, AggregateType aggregateType, Long aggregateId) {
        return new LegacyMigrationLog(true, aggregateType, aggregateId, userId);
    }

    public static LegacyMigrationLog fail(Long userId, AggregateType aggregateType, Long aggregateId) {
        return new LegacyMigrationLog(false, aggregateType, aggregateId, userId);
    }
}