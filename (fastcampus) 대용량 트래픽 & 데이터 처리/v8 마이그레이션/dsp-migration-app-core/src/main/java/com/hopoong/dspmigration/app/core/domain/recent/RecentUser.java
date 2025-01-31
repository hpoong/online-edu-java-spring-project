package com.hopoong.dspmigration.app.core.domain.recent;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecentUser implements MigratedEntity {

    @Id
    private Long id;

    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime migratedAt;

    public static RecentUser migrated(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        return new RecentUser(id, name, createdAt, updatedAt, deletedAt, LocalDateTime.now());
    }

}
