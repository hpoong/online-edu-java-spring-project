package com.hopoong.dspmigration.app.modern.message;

import java.time.LocalDateTime;

public record LegacyDomainMessage(AggregateType aggregateType, Long aggregateId, Long ownerId,
                                  LocalDateTime occurredOn) {

}