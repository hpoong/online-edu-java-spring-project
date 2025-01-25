package com.hopoong.dspmigration.app.legacy.api.keyword.model;

import java.time.LocalDateTime;

public record LegacyKeywordResp(Long id, String text, Long adGroupId, LocalDateTime createdAt,
                                LocalDateTime deletedAt) {

  public static LegacyKeywordResp from(LegacyKeywordResult keyword) {
    return new LegacyKeywordResp(keyword.id(), keyword.text(), keyword.adGroupId(),
        keyword.createdAt(), keyword.deletedAt());
  }
}
