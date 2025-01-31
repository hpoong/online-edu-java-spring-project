package com.hopoong.dspmigration.app.core.converter;

import com.hopoong.dspmigration.app.core.domain.legacyad.DeletableEntity;
import com.hopoong.dspmigration.app.core.domain.recent.MigratedEntity;

public interface LegacyConverter<Legacy extends DeletableEntity, Recent extends MigratedEntity> {

    Recent convert(Legacy legacy);
}