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
public class RecentKeyword implements MigratedEntity {
    @Id
    private Long id;

    private String text;
    private Long campaignId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime migratedAt;
}
