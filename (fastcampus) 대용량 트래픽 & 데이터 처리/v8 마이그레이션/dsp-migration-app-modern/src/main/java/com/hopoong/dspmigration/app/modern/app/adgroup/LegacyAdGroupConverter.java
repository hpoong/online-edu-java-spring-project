package com.hopoong.dspmigration.app.modern.app.adgroup;

import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyConverter;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyCampaign;
import com.hopoong.dspmigration.app.modern.domain.recent.RecentCampaign;
import com.hopoong.dspmigration.app.modern.repository.legacy.campaign.LegacyCampaignRepository;
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
