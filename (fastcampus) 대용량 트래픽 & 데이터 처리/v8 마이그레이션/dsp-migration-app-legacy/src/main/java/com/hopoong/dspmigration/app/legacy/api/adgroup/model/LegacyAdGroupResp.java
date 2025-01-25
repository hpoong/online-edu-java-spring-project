package com.hopoong.dspmigration.app.legacy.api.adgroup.model;

import java.time.LocalDateTime;

public record LegacyAdGroupResp(Long id, String name, Long campaignId, String linkUrl,
                                LocalDateTime createdAt, LocalDateTime updatedAt,
                                LocalDateTime deletedAt) {


  public static LegacyAdGroupResp from(LegacyAdGroupResult adGroup) {
    return new LegacyAdGroupResp(adGroup.id(), adGroup.name(), adGroup.campaignId(),
        adGroup.linkUrl(), adGroup.createdAt(), adGroup.updatedAt(),
        adGroup.deletedAt());
  }
}
