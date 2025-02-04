package com.hopoong.dspmigration.app.core.converter;

import com.hopoong.dspmigration.app.core.domain.legacyad.DeletableEntity;
import com.hopoong.dspmigration.app.core.domain.recent.MigratedEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class LegacyMigrationService<Legacy extends DeletableEntity, Recent extends MigratedEntity> implements MigrationService {

    protected CrudRepository<Legacy, Long> legacyRepository;
    protected LegacyConverter<Legacy, Recent> converter;
    protected CrudRepository<Recent, Long> recentRepository;

    public LegacyMigrationService(CrudRepository<Legacy, Long> legacyRepository, LegacyConverter<Legacy, Recent> converter, CrudRepository<Recent, Long> recentRepository) {
        this.legacyRepository = legacyRepository;
        this.converter = converter;
        this.recentRepository = recentRepository;
    }

    @Override
    public boolean migrate(Long id) {
        try {
            migrate(findLegacy(id));
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    protected void migrate(Legacy legacy) {
        if (legacy.isDeleted()) {
            deleteRecent(legacy.getId());
        } else {
            saveRecent(convert(legacy));
        }
    }

    private void deleteRecent(Long id) {
        recentRepository.findById(id).ifPresent(recent -> recentRepository.delete(recent));
    }

    private void saveRecent(Recent convert) {
        recentRepository.save(convert);
    }

    private Legacy findLegacy(Long id) {
        return legacyRepository.findById(id).orElseThrow(() -> new RuntimeException("%s 값이 없습니다.".formatted(id)));
    }

    private Recent convert(Legacy legacy) {
        return converter.convert(legacy);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean migrate(List<Legacy> legacies) {
        try {
            saveRecents(convert(legacies));
            return true;
        } catch (RuntimeException e) {
            log.error("list migration error", e);
            return false;
        }
    }

    private List<Recent> convert(List<Legacy> legacies) {
        return legacies.stream().map(this::convert).collect(Collectors.toList());
    }

    private void saveRecents(List<Recent> convert) {
        recentRepository.saveAll(convert);
    }

}
