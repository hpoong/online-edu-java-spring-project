package com.hopoong.dspmigration.app.core.app.keyword;

import com.hopoong.dspmigration.app.core.app.pagemigration.PageLegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyKeyword;
import com.hopoong.dspmigration.app.core.domain.migration.keyword.KeywordPageMigration;
import com.hopoong.dspmigration.app.core.domain.recent.RecentKeyword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PageLegacyKeywordMigrationService extends PageLegacyMigrationService<KeywordPageMigration, LegacyKeyword, RecentKeyword> {
}
