package com.hopoong.dspmigration.app.modern.app.legacyad;

import com.hopoong.dspmigration.app.modern.domain.legacyad.DeletableEntity;
import com.hopoong.dspmigration.app.modern.domain.recent.MigratedEntity;

public interface LegacyConverter<Legacy extends DeletableEntity, Recent extends MigratedEntity> {

    Recent convert(Legacy legacy);
}