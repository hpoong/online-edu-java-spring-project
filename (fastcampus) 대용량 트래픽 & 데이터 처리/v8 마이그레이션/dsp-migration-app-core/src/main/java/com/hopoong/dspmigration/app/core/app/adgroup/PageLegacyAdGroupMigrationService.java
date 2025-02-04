package com.hopoong.dspmigration.app.core.app.adgroup;

import com.hopoong.dspmigration.app.core.app.pagemigration.PageLegacyMigrationService;
import com.hopoong.dspmigration.app.core.converter.LegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.core.domain.migration.adgroup.AdGroupPageMigration;
import com.hopoong.dspmigration.app.core.domain.recent.RecentCampaign;
import com.hopoong.dspmigration.app.core.repository.legacy.adgroup.LegacyAdGroupRepository;
import com.hopoong.dspmigration.app.core.repository.migration.PageMigrationRepository;
import com.hopoong.dspmigration.app.core.repository.migration.adgroup.AdGroupPageMigrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageLegacyAdGroupMigrationService extends
        PageLegacyMigrationService<AdGroupPageMigration, LegacyAdGroup, RecentCampaign> {
    public PageLegacyAdGroupMigrationService(
            AdGroupPageMigrationRepository adGroupMigrationRepository,
            LegacyAdGroupRepository legacyAdGroupRepository,
            LegacyAdGroupMigrationService legacyAdGroupMigrationService) {
        super(adGroupMigrationRepository, legacyAdGroupRepository, legacyAdGroupMigrationService);
    }
    @Override
    protected AdGroupPageMigration firstPageMigration(Long userId, boolean isSuccess,
                                                      Page<LegacyAdGroup> legacyPage) {
        return AdGroupPageMigration.first(isSuccess, userId, PAGE_SIZE, legacyPage.getTotalElements());
    }
}
