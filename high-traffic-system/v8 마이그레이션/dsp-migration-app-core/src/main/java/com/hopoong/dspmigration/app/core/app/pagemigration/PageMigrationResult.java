package com.hopoong.dspmigration.app.core.app.pagemigration;

public record PageMigrationResult(Long userId, int pageNumber, int totalPages, Long totalCount, boolean isSuccess) {
}