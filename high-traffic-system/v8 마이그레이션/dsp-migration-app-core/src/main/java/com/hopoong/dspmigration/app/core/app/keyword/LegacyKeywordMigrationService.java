package com.hopoong.dspmigration.app.core.app.keyword;

import com.hopoong.dspmigration.app.core.converter.LegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyKeyword;
import com.hopoong.dspmigration.app.core.domain.recent.RecentKeyword;
import com.hopoong.dspmigration.app.core.repository.legacy.keyword.LegacyKeywordRepository;
import com.hopoong.dspmigration.app.core.repository.recent.keyword.RecentKeywordRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyKeywordMigrationService extends LegacyMigrationService<LegacyKeyword, RecentKeyword> {


    public LegacyKeywordMigrationService(LegacyKeywordRepository legacyKeywordRepository,
                                         LegacyKeywordConverter legacyKeywordConverter,
                                         RecentKeywordRepository recentKeywordRepository
                                         ) {
        super(legacyKeywordRepository, legacyKeywordConverter, recentKeywordRepository);
    }
}
