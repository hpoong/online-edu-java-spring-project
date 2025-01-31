package com.hopoong.dspmigration.app.core.app.user;

import com.hopoong.dspmigration.app.core.converter.LegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyUser;
import com.hopoong.dspmigration.app.core.domain.recent.RecentUser;
import com.hopoong.dspmigration.app.core.repository.legacy.user.LegacyUserRepository;
import com.hopoong.dspmigration.app.core.repository.recent.user.RecentUserRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyUserMigrationService extends LegacyMigrationService<LegacyUser, RecentUser> {

    public LegacyUserMigrationService(
            LegacyUserRepository legacyUserRepository,
            LegacyUserConverter converter,
            RecentUserRepository recentUserRepository) {
        super(legacyUserRepository, converter, recentUserRepository);
    }
}
