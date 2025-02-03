package com.hopoong.dspmigration.app.core.dispatcher;

import com.hopoong.dspmigration.app.core.app.pagemigration.PageMigrationResult;
import com.hopoong.dspmigration.app.core.domain.AggregateType;

public record PageLegacyMigrationLog(Long userId, AggregateType aggregateType, int pageNumber, int totalPages, Long totalCount, boolean isSuccess) {


    public static PageLegacyMigrationLog success(PageMigrationResult result, AggregateType aggregateType) {
        return new PageLegacyMigrationLog(result.userId(), aggregateType, result.pageNumber(),
                result.totalPages(), result.totalCount(), true);
    }


    public static PageLegacyMigrationLog fail(PageMigrationResult result, AggregateType aggregateType) {
        return new PageLegacyMigrationLog(result.userId(), aggregateType, result.pageNumber(),
                result.totalPages(), result.totalCount(), false);
    }
}