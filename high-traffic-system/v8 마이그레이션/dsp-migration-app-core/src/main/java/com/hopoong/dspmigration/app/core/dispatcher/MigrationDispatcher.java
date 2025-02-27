package com.hopoong.dspmigration.app.core.dispatcher;


import com.hopoong.dspmigration.app.core.app.adgroup.LegacyAdGroupMigrationService;
import com.hopoong.dspmigration.app.core.app.campaign.LegacyCampaignMigrationService;
import com.hopoong.dspmigration.app.core.app.keyword.LegacyKeywordMigrationService;
import com.hopoong.dspmigration.app.core.app.legacy_user.LegacyUserMigrationService;
import com.hopoong.dspmigration.app.core.app.migration_user.MigrationUserService;
import com.hopoong.dspmigration.app.core.converter.MigrationService;
import com.hopoong.dspmigration.app.core.domain.AggregateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationDispatcher {

    private final MigrationUserService migrationUserService;
    private final LegacyUserMigrationService legacyUserMigrationService;
    private final LegacyCampaignMigrationService legacyCampaignMigrationService;
    private final LegacyAdGroupMigrationService legacyAdGroupMigrationService;
    private final LegacyKeywordMigrationService legacyKeywordMigrationService;


    public boolean dispatch(Long userId, Long aggregateId, AggregateType aggregateType) {
        if (isDisagreed(userId)) { // 데이터 없으면 true.
            return false;
        }
        return migrate(userId, aggregateId, aggregateType);
    }

    private boolean isDisagreed(Long userId) {
        return migrationUserService.isDisagreed(userId);
    }

    private boolean migrate(Long userId, Long aggregateId, AggregateType aggregateType) {
        MigrationService service = switch (aggregateType) {
            case USER -> legacyUserMigrationService;
            case CAMPAIGN -> legacyCampaignMigrationService;
            case ADGROUP -> legacyAdGroupMigrationService;
            case KEYWORD -> legacyKeywordMigrationService;
        };
        boolean result = service.migrate(aggregateId);
        logMigrationResult(userId, result, aggregateType, aggregateId);
        return result;
    }

    private void logMigrationResult(Long userId, boolean result, AggregateType aggregateType, Long aggregateId) {
        if (result) {
            log.info("{}", LegacyMigrationLog.success(userId, aggregateType, aggregateId));
        } else {
            log.error("{}", LegacyMigrationLog.fail(userId, aggregateType, aggregateId));
        }
    }
}
