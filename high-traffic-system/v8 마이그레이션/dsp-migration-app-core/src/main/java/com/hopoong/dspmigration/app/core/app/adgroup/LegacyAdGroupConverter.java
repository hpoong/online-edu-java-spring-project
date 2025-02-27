package com.hopoong.dspmigration.app.core.app.adgroup;

import com.hopoong.dspmigration.app.core.converter.LegacyConverter;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyCampaign;
import com.hopoong.dspmigration.app.core.domain.recent.RecentCampaign;
import com.hopoong.dspmigration.app.core.repository.legacy.campaign.LegacyCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyAdGroupConverter implements LegacyConverter<LegacyAdGroup, RecentCampaign> {

    private final LegacyCampaignRepository legacyCampaignRepository;

    public RecentCampaign convert(LegacyAdGroup legacyAdGroup) {
        LegacyCampaign legacyCampaign = legacyCampaignRepository.findById(legacyAdGroup.getCampaignId())
                .orElseThrow(RuntimeException::new);
        return RecentCampaign.migrated(legacyAdGroup.getId(),
                legacyCampaign.getName() + "-" + legacyAdGroup.getName(),
                legacyCampaign.getUserId(), legacyCampaign.getBudget(), legacyAdGroup.getLinkUrl(),
                legacyAdGroup.getCreatedAt(), legacyAdGroup.getUpdatedAt(),
                legacyAdGroup.getDeletedAt());
    }
}
