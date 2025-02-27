package com.hopoong.dspmigration.app.legacy.api.adgroup.model;

import com.hopoong.dspmigration.app.legacy.domain.LegacyAdGroup;

import java.time.LocalDateTime;

public record LegacyAdGroupResult(Long id, String name, Long campaignId, Long userId,
                                  String linkUrl, LocalDateTime createdAt,
                                  LocalDateTime updatedAt, LocalDateTime deletedAt) {

    public static LegacyAdGroupResult from(LegacyAdGroup adGroup) {
        return new LegacyAdGroupResult(adGroup.getId(), adGroup.getName(), adGroup.getCampaignId(),
                adGroup.getUserId(), adGroup.getLinkUrl(), adGroup.getCreatedAt(),
                adGroup.getUpdatedAt(), adGroup.getDeletedAt());
    }

}