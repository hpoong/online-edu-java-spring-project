package com.hopoong.dspmigration.app.core.app.migration_user;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;
import com.hopoong.dspmigration.app.core.repository.migration.user.MigrationUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class MigrationUserServiceTest {
    @Mock
    MigrationUserRepository repository;

    @InjectMocks
    MigrationUserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void 동의하지_않았으면_true() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        boolean result = service.isDisagreed(1L);
        assertThat(result).isTrue();
    }

    @Test
    void 동의했으면_false() {
        when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));
        boolean result = service.isDisagreed(1L);
        assertThat(result).isFalse();
    }
}