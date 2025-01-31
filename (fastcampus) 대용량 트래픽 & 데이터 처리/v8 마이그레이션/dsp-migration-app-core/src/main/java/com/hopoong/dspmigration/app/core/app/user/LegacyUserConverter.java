package com.hopoong.dspmigration.app.core.app.user;

import com.hopoong.dspmigration.app.core.converter.LegacyConverter;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyUser;
import com.hopoong.dspmigration.app.core.domain.recent.RecentUser;
import org.springframework.stereotype.Component;

@Component
public class LegacyUserConverter implements LegacyConverter<LegacyUser, RecentUser> {

    @Override
    public RecentUser convert(LegacyUser legacyUser) {
        return RecentUser.migrated(legacyUser.getId(), legacyUser.getName(),
                legacyUser.getCreatedAt(), legacyUser.getUpdatedAt(), legacyUser.getDeletedAt());
    }
}



