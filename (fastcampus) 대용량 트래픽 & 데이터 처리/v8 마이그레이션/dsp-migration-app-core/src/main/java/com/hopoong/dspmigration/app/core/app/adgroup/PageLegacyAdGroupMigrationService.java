package com.hopoong.dspmigration.app.core.app.adgroup;

import com.hopoong.dspmigration.app.core.app.pagemigration.PageLegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.core.domain.migration.adgroup.AdGroupPageMigration;
import com.hopoong.dspmigration.app.core.domain.recent.RecentCampaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageLegacyAdGroupMigrationService extends PageLegacyMigrationService<AdGroupPageMigration, LegacyAdGroup, RecentCampaign> {
}
