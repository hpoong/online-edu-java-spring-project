package com.hopoong.dspmigration.app.legacy.api.adgroup;

import com.hopoong.dspmigration.app.legacy.domain.LegacyAdGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyAdGroupRepository extends CrudRepository<LegacyAdGroup, Long> {
}
