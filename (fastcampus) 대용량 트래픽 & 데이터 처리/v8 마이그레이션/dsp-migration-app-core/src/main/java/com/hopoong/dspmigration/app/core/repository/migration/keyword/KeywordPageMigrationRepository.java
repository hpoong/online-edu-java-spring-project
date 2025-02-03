package com.hopoong.dspmigration.app.core.repository.migration.keyword;

import com.hopoong.dspmigration.app.core.domain.migration.keyword.KeywordPageMigration;
import com.hopoong.dspmigration.app.core.repository.migration.PageMigrationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordPageMigrationRepository extends PageMigrationRepository<KeywordPageMigration> {
}