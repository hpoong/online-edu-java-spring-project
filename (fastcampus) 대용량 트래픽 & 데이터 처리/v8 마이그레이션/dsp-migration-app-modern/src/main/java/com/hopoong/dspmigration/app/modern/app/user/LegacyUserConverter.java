package com.hopoong.dspmigration.app.modern.app.user;

import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyConverter;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyUser;
import com.hopoong.dspmigration.app.modern.domain.recent.RecentUser;
import org.springframework.stereotype.Component;

@Component
public class LegacyUserConverter implements LegacyConverter<LegacyUser, RecentUser> {

    @Override
    public RecentUser convert(LegacyUser legacyUser) {
        return RecentUser.migrated(legacyUser.getId(), legacyUser.getName(),
                legacyUser.getCreatedAt(), legacyUser.getUpdatedAt(), legacyUser.getDeletedAt());
    }
}



