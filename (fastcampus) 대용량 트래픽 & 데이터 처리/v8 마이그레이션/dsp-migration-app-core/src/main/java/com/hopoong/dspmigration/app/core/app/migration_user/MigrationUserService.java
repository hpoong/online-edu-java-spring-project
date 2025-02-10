package com.hopoong.dspmigration.app.core.app.migration_user;


import com.hopoong.dspmigration.app.core.app.legacy_user.LegacyUserMigrationService;
import com.hopoong.dspmigration.app.core.converter.MigrationService;
import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;
import com.hopoong.dspmigration.app.core.repository.migration.user.MigrationUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MigrationUserService implements MigrationService {

    private final MigrationUserRepository repository;
    private final LegacyUserMigrationService legacyUserMigrationService;

    @Transactional
    public MigrationUserResult agree(Long userId) {
        repository.findById(userId).ifPresent(migrationUser -> {
            throw new RuntimeException(String.format("User [ID: %d] already agreed", userId));
        });
        return MigrationUserResult.from(repository.save(MigrationUser.agreed(userId)));
    }

    private MigrationUser find(Long userId) {
        return repository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public MigrationUserResult findById(Long userId) {
        return MigrationUserResult.from(find(userId));
    }

    public boolean isDisagreed(Long migrationUserId) {
        return repository.findById(migrationUserId).isEmpty();
    }

    @Transactional
    public MigrationUserResult startMigration(Long userId) throws RuntimeException {
        boolean result = migrate(userId);
        if (result) {
            return progressMigration(userId);
        }
        throw new RuntimeException();
    }

    @Override
    public boolean migrate(Long id) {
        return legacyUserMigrationService.migrate(id);
    }

    @Transactional
    public MigrationUserResult progressMigration(Long userId) {
        MigrationUser migrationUser = find(userId);
        migrationUser.progressMigration();
        return MigrationUserResult.from(repository.save(migrationUser));
    }

    @Transactional
    public MigrationUserResult retry(Long userId) {
        MigrationUser migrationUser = find(userId);
        migrationUser.retry();
        return MigrationUserResult.from(repository.save(migrationUser));
    }




}
