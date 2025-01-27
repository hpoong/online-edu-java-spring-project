package com.hopoong.dspmigration.app.modern.app.user;

import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyMigrationService;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyUser;
import com.hopoong.dspmigration.app.modern.domain.recent.RecentUser;
import com.hopoong.dspmigration.app.modern.repository.legacy.user.LegacyUserRepository;
import com.hopoong.dspmigration.app.modern.repository.recent.user.RecentUserRepository;
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
