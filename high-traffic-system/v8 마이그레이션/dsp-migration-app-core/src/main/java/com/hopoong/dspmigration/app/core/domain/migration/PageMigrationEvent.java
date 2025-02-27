package com.hopoong.dspmigration.app.core.domain.migration;

import com.hopoong.dspmigration.app.core.domain.AggregateType;

public record PageMigrationEvent(AggregateType aggregateType, Long userId, boolean isFinished) {
}