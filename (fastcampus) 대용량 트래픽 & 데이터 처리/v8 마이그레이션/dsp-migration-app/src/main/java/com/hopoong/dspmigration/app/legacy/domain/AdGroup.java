package com.hopoong.dspmigration.app.legacy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AdGroup {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
