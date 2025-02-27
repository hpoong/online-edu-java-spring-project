package com.hopoong.dspmigration.app.recent.message;

import com.hopoong.dspmigration.app.core.domain.AggregateType;

import java.time.LocalDateTime;

public record LegacyDomainMessage(AggregateType aggregateType, Long aggregateId, Long ownerId,
                                  LocalDateTime occurredOn) {

}