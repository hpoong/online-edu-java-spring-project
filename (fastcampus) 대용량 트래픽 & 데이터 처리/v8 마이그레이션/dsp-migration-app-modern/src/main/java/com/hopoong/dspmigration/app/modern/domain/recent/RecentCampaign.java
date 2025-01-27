package com.hopoong.dspmigration.app.modern.domain.recent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class RecentCampaign implements MigratedEntity {

    @Id
    private Long id;

    private String name;
    private Long userId;
    private Long budget;
    private String linkUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime migratedAt;

    public static RecentCampaign migrated(Long id, String name, Long userId, Long budget,
                                          String linkUrl,
                                          LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        return new RecentCampaign(id, name, userId, budget, linkUrl, createdAt, updatedAt, deletedAt,
                LocalDateTime.now());
    }

}