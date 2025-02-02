package com.hopoong.dspmigration.app.internal.api;

import com.hopoong.dspmigration.app.core.domain.AggregateType;

public record MigrationRetryRequest(Long userId, Long aggregateId, AggregateType aggregateType) {

}