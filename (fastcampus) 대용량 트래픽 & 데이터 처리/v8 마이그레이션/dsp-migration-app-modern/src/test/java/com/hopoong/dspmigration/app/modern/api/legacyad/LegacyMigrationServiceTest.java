package com.hopoong.dspmigration.app.modern.api.legacyad;

import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyConverter;
import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyMigrationService;
import com.hopoong.dspmigration.app.modern.domain.legacyad.DeletableEntity;
import com.hopoong.dspmigration.app.modern.domain.recent.MigratedEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.CrudRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;


class LegacyMigrationServiceTest {

    @Mock
    CrudRepository<DeletableEntity, Long> legacyRepository;
    @Mock
    CrudRepository<MigratedEntity, Long> recentRepository;
    @Mock
    LegacyConverter<DeletableEntity, MigratedEntity> converter;

    LegacyMigrationService<?, ?> service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new LegacyMigrationService<>(
                legacyRepository, converter, recentRepository) {
            @Override
            public boolean migrate(Long id) {
                return super.migrate(id);
            }
        };
    }

    @Test
    void 레거시_도메인_조회시_데이터가_없으면_마이그레이션_실패() {
        when(legacyRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = service.migrate(1L);

        assertThat(result).isFalse();
    }

    @Test
    void 레거시_도메인이_삭제되었고_마이그레이션된_적_있으면_삭제() {
        삭제된_레거시가_조회됨(1L);
        when(recentRepository.findById(1L)).thenReturn(
                Optional.of(() -> LocalDateTime.now().minusHours(1)));

        boolean result = service.migrate(1L);

        assertAll(() -> assertThat(result).isTrue(),
                () -> verify(recentRepository, times(1)).delete(any()));
    }

    private void 삭제된_레거시가_조회됨(Long id) {
        when(legacyRepository.findById(id)).thenReturn(Optional.of(new DeletableEntity() {
            @Override
            public LocalDateTime getDeletedAt() {
                return LocalDateTime.now();
            }

            @Override
            public Long getId() {
                return id;
            }
        }));
    }



}