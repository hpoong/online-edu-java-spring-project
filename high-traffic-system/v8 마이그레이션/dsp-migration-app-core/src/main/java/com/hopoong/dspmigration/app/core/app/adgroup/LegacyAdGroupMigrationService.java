package com.hopoong.dspmigration.app.core.app.adgroup;

import com.hopoong.dspmigration.app.core.converter.LegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.core.domain.recent.RecentCampaign;
import com.hopoong.dspmigration.app.core.repository.legacy.adgroup.LegacyAdGroupRepository;
import com.hopoong.dspmigration.app.core.repository.recent.campaign.RecentCampaignRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyAdGroupMigrationService extends LegacyMigrationService<LegacyAdGroup, RecentCampaign> {



    public LegacyAdGroupMigrationService(LegacyAdGroupRepository legacyAdGroupRepository,
                                         LegacyAdGroupConverter legacyAdGroupConverter,
                                         RecentCampaignRepository recentCampaignRepository
                                         ) {
        super(legacyAdGroupRepository, legacyAdGroupConverter, recentCampaignRepository);
    }

}
