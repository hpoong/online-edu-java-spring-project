package com.hopoong.dspmigration.app.core.dispatcher;

import com.hopoong.dspmigration.app.core.app.adgroup.PageLegacyAdGroupMigrationService;
import com.hopoong.dspmigration.app.core.app.keyword.PageLegacyKeywordMigrationService;
import com.hopoong.dspmigration.app.core.app.pagemigration.PageLegacyMigrationService;
import com.hopoong.dspmigration.app.core.app.pagemigration.PageMigrationResult;
import com.hopoong.dspmigration.app.core.domain.AggregateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PageMigrationDispatcher {

    private final PageLegacyAdGroupMigrationService adGroupMigrationService;
    private final PageLegacyKeywordMigrationService keywordMigrationService;

    public boolean dispatch(Long userId, AggregateType aggregateType) {
        PageLegacyMigrationService<?, ?, ?> service = switch (aggregateType) {
            case ADGROUP -> adGroupMigrationService;
            case KEYWORD -> keywordMigrationService;
            default -> throw new RuntimeException();
        };
        PageMigrationResult result = service.migrate(userId);
        logMigrationResult(result, aggregateType);
        return result.isSuccess();
    }


    private void logMigrationResult(PageMigrationResult result, AggregateType aggregateType) {
        if (result.isSuccess()) {
            log.info("{}", PageLegacyMigrationLog.success(result, aggregateType));
        } else {
            log.error("{}", PageLegacyMigrationLog.fail(result, aggregateType));
        }
    }
}