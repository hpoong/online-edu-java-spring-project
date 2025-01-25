package com.hopoong.dspmigration.app.legacy.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LegacyUserTest {


    LegacyUser user = LegacyUser.of("name");

    @Test
    void updateName() {
        LocalDateTime before = LocalDateTime.now();
        user.updateName("newName");
        LocalDateTime after = LocalDateTime.now();

        assertAll(
                () -> assertThat(user.getName()).isEqualTo("newName"),
                () -> assertThat(user.getUpdatedAt())
                        .isAfterOrEqualTo(before)
                        .isBeforeOrEqualTo(after));
    }

    @Test
    void delete() {
        LocalDateTime before = LocalDateTime.now();
        user.delete();
        LocalDateTime after = LocalDateTime.now();

        // 삭제 시간이 before와 after 사이에 있는지 확인
        assertThat(user.getDeletedAt())
                .isAfterOrEqualTo(before)
                .isBeforeOrEqualTo(after);
    }
}