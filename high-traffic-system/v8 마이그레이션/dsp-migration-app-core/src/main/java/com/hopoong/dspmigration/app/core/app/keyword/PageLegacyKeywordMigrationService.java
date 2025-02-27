package com.hopoong.dspmigration.app.core.app.keyword;

import com.hopoong.dspmigration.app.core.app.pagemigration.PageLegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyKeyword;
import com.hopoong.dspmigration.app.core.domain.migration.keyword.KeywordPageMigration;
import com.hopoong.dspmigration.app.core.domain.recent.RecentKeyword;
import com.hopoong.dspmigration.app.core.repository.legacy.keyword.LegacyKeywordRepository;
import com.hopoong.dspmigration.app.core.repository.migration.keyword.KeywordPageMigrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageLegacyKeywordMigrationService extends PageLegacyMigrationService<KeywordPageMigration, LegacyKeyword, RecentKeyword> {
    public PageLegacyKeywordMigrationService(
            KeywordPageMigrationRepository keywordMigrationRepository,
            LegacyKeywordRepository legacyKeywordRepository,
            LegacyKeywordMigrationService legacyKeywordMigrationService) {
        super(keywordMigrationRepository, legacyKeywordRepository, legacyKeywordMigrationService);
    }
    @Override
    protected KeywordPageMigration firstPageMigration(Long userId, boolean isSuccess,
                                                      Page<LegacyKeyword> legacyPage) {
        return KeywordPageMigration.first(isSuccess, userId, PAGE_SIZE, legacyPage.getTotalElements());
    }
}