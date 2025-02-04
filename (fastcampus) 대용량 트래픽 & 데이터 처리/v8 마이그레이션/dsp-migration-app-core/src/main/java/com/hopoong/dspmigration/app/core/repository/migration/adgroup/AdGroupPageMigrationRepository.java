package com.hopoong.dspmigration.app.core.repository.migration.adgroup;

import com.hopoong.dspmigration.app.core.domain.migration.adgroup.AdGroupPageMigration;
import com.hopoong.dspmigration.app.core.repository.migration.PageMigrationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdGroupPageMigrationRepository extends PageMigrationRepository<AdGroupPageMigration> {
}
