package com.hopoong.dspmigration.app.legacy.api.user.model;

import java.time.LocalDateTime;

public record LegacyUserResp(Long id, String name, LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             LocalDateTime deletedAt) {

  public static LegacyUserResp from(LegacyUserResult user) {
    return new LegacyUserResp(user.id(), user.name(), user.createdAt(),
        user.updatedAt(), user.deletedAt());
  }
}
