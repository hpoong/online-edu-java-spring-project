package com.hopoong.dspmigration.app.modern.app.keyword;

import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyMigrationService;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyKeyword;
import com.hopoong.dspmigration.app.modern.domain.recent.RecentKeyword;
import com.hopoong.dspmigration.app.modern.repository.legacy.keyword.LegacyKeywordRepository;
import com.hopoong.dspmigration.app.modern.repository.recent.keyword.RecentKeywordRepository;
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
