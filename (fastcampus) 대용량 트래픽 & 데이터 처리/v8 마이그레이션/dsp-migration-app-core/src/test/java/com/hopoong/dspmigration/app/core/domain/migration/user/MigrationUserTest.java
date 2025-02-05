package com.hopoong.dspmigration.app.core.domain.migration.user;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MigrationUserTest {



    @Test
    void 재시도하면_상태를_RETRIED로_바꾸고_이전_상태를_저장() {
        MigrationUser user = MigrationUser.agreed(1L);
        user.progressMigration();
        user.retry();
        assertAll(() -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.RETRIED),
                () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.USER_FINISHED));
    }

}