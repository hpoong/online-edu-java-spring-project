package com.hopoong.dspmigration.app.modern.app.campaign;

import com.hopoong.dspmigration.app.modern.app.adgroup.LegacyAdGroupMigrationService;
import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyMigrationService;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyCampaign;
import com.hopoong.dspmigration.app.modern.domain.recent.RecentCampaign;
import com.hopoong.dspmigration.app.modern.repository.legacy.adgroup.LegacyAdGroupRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyCampaignMigrationService extends LegacyMigrationService<LegacyCampaign, RecentCampaign> {

    private final LegacyAdGroupMigrationService legacyAdGroupMigrationService;
    private final LegacyAdGroupRepository legacyAdGroupRepository;


    public LegacyCampaignMigrationService(
            CrudRepository<LegacyCampaign, Long> legacyRepository,
            CrudRepository<RecentCampaign, Long> recentRepository,
            LegacyAdGroupMigrationService legacyAdGroupMigrationService,
            LegacyAdGroupRepository legacyAdGroupRepository
    ) {
        super(legacyRepository, null, recentRepository);
        this.legacyAdGroupMigrationService = legacyAdGroupMigrationService;
        this.legacyAdGroupRepository = legacyAdGroupRepository;
    }

    @Override
    public void migrate(LegacyCampaign legacyCampaign) {
        for (LegacyAdGroup legacyAdGroup : legacyAdGroupRepository.findAllByCampaignIdAndDeletedAtIsNull(legacyCampaign.getId())) {
            legacyAdGroupMigrationService.migrate(legacyAdGroup.getId());
        }
    }
}
