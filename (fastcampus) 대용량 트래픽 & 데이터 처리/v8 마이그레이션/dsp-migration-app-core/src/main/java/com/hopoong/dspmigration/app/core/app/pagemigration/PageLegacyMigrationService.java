package com.hopoong.dspmigration.app.core.app.pagemigration;

import com.hopoong.dspmigration.app.core.domain.legacyad.DeletableEntity;
import com.hopoong.dspmigration.app.core.domain.migration.PageMigration;
import com.hopoong.dspmigration.app.core.domain.recent.MigratedEntity;
import org.springframework.transaction.annotation.Transactional;

public abstract class PageLegacyMigrationService<P extends PageMigration<P>, Legacy extends DeletableEntity, Recent extends MigratedEntity> {



}
