package com.hopoong.dspmigration.app.batch.message;

import com.hopoong.dspmigration.app.core.domain.AggregateType;

public record PageMigrationMessage(Long userId, AggregateType aggregateType, boolean isFinished) {
}