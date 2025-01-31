package com.hopoong.dspmigration.app.core.domain.recent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class RecentKeyword implements MigratedEntity {
    @Id
    private Long id;

    private String text;
    private Long campaignId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime migratedAt;

    public static RecentKeyword migrated(Long id, String text, Long campaignId, Long userId,
                                         LocalDateTime createdAt, LocalDateTime deletedAt) {
        return new RecentKeyword(id, text, campaignId, userId, createdAt, deletedAt,
                LocalDateTime.now());
    }
}
