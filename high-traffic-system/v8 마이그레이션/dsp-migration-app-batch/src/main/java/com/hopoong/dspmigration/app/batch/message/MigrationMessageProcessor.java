package com.hopoong.dspmigration.app.batch.message;

import com.hopoong.dspmigration.app.core.app.migration_user.MigrationUserService;
import com.hopoong.dspmigration.app.core.dispatcher.PageMigrationDispatcher;
import com.hopoong.dspmigration.app.core.domain.AggregateType;
import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationMessageProcessor {

    private final MigrationUserService migrationUserService;
    private final PageMigrationDispatcher pageMigrationDispatcher;


    public void progressMigration(MigrationUserStatus prevStatus, MigrationUserStatus status,
                                  Long userId) {
        switch (status) {
            case RETRIED -> progressMigration(prevStatus, userId);
            case AGREED, USER_FINISHED, ADGROUP_FINISHED, KEYWORD_FINISHED ->
                    progressMigration(status, userId);
            default -> log.info("userId: {}, status: {}, prevStatus: {}", userId, status, prevStatus);
        }
    }

    public void progressMigration(MigrationUserStatus status, Long userId) {
        switch (status) {
            case AGREED -> startMigration(userId);
            case USER_FINISHED -> dispatch(userId, AggregateType.ADGROUP);
            case ADGROUP_FINISHED -> dispatch(userId, AggregateType.KEYWORD);
            case KEYWORD_FINISHED -> migrationUserService.progressMigration(userId);
        }
    }

    private void startMigration(Long userId) {
        try {
            migrationUserService.startMigration(userId);
        } catch (RuntimeException e) {
            log.error("start migration failed", e);
        }
    }

    public void processPageMigration(Long userId, AggregateType aggregateType, boolean isFinished) {
        if (isFinished) {
            migrationUserService.progressMigration(userId);
        } else {
            dispatch(userId, aggregateType);
        }
    }


    private void dispatch(Long userId, AggregateType aggregateType) {
        pageMigrationDispatcher.dispatch(userId, aggregateType);
    }
}