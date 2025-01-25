package com.hopoong.dspmigration.app.modern.repository.legacy.adgroup;

import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyAdGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyAdGroupRepository extends CrudRepository<LegacyAdGroup, Long> {
}
