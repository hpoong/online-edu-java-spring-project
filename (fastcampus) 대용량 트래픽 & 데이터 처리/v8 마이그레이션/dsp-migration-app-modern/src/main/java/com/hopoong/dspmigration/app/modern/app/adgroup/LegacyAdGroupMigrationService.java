package com.hopoong.dspmigration.app.modern.app.adgroup;

import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyMigrationService;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.modern.domain.recent.RecentCampaign;
import com.hopoong.dspmigration.app.modern.repository.legacy.adgroup.LegacyAdGroupRepository;
import com.hopoong.dspmigration.app.modern.repository.recent.campaign.RecentCampaignRepository;
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
