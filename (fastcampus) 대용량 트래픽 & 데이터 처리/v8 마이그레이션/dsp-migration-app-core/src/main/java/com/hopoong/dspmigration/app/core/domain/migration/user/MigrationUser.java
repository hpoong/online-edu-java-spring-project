package com.hopoong.dspmigration.app.core.domain.migration.user;


import com.hopoong.dspmigration.app.core.domain.migration.user.event.MigrationAgreedEvent;
import com.hopoong.dspmigration.app.core.domain.migration.user.event.MigrationProgressedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class MigrationUser extends AbstractAggregateRoot<MigrationUser> {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private MigrationUserStatus status;
    private LocalDateTime agreedAt;
    private LocalDateTime updateAt;

    @Transient
    private MigrationUserStatus prevStatus;


    private MigrationUser(Long id, LocalDateTime agreedAt) {
        this.id = id;
        this.status = MigrationUserStatus.AGREED;
        this.agreedAt = agreedAt;
        this.updateAt = agreedAt;
        registerEvent(new MigrationAgreedEvent(this));
    }

    public static MigrationUser agreed(Long id) {
        return new MigrationUser(id, LocalDateTime.now());
    }

    public void progressMigration() {
        prevStatus = status;
        status = status.next();
        updateAt = LocalDateTime.now();
        registerEvent(new MigrationProgressedEvent(this));
    }


}
