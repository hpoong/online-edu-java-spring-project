package com.hopoong.dspmigration.app.modern.domain.recent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
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
}