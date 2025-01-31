package com.hopoong.dspmigration.app.core.dispatcher;


import com.hopoong.dspmigration.app.core.app.adgroup.LegacyAdGroupMigrationService;
import com.hopoong.dspmigration.app.core.app.campaign.LegacyCampaignMigrationService;
import com.hopoong.dspmigration.app.core.app.keyword.LegacyKeywordMigrationService;
import com.hopoong.dspmigration.app.core.app.user.LegacyUserMigrationService;
import com.hopoong.dspmigration.app.core.converter.MigrationService;
import com.hopoong.dspmigration.app.core.domain.AggregateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationDispatcher {

    private final LegacyUserMigrationService legacyUserMigrationService;
    private final LegacyCampaignMigrationService legacyCampaignMigrationService;
    private final LegacyAdGroupMigrationService legacyAdGroupMigrationService;
    private final LegacyKeywordMigrationService legacyKeywordMigrationService;


    public boolean dispatch(Long aggregateId, AggregateType aggregateType) {
        return migrate(aggregateId, aggregateType);
    }

    private boolean migrate(Long aggregateId, AggregateType aggregateType) {
        MigrationService service = switch (aggregateType) {
            case USER -> legacyUserMigrationService;
            case CAMPAIGN -> legacyCampaignMigrationService;
            case ADGROUP -> legacyAdGroupMigrationService;
            case KEYWORD -> legacyKeywordMigrationService;
        };
        boolean result = service.migrate(aggregateId);
        logMigrationResult(result, aggregateType, aggregateId);
        return result;
    }

    private void logMigrationResult(boolean result, AggregateType aggregateType, Long aggregateId) {
        if (result) {
            log.info("{}", LegacyMigrationLog.success(aggregateType, aggregateId));
        } else {
            log.error("{}", LegacyMigrationLog.fail(aggregateType, aggregateId));
        }
    }
}
