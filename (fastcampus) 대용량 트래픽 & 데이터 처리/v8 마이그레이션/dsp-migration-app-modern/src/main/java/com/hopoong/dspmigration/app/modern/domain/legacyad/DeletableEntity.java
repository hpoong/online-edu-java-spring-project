package com.hopoong.dspmigration.app.modern.domain.legacyad;

import java.time.LocalDateTime;
import java.util.Optional;


public interface DeletableEntity {

    default boolean isDeleted() {
        return Optional.ofNullable(getDeletedAt()).isPresent();
    }

    LocalDateTime getDeletedAt();

    Long getId();

}