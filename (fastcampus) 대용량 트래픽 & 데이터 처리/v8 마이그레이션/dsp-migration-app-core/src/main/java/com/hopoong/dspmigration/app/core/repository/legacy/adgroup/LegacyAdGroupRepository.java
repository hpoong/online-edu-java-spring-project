package com.hopoong.dspmigration.app.core.repository.legacy.adgroup;

import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyAdGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegacyAdGroupRepository extends CrudRepository<LegacyAdGroup, Long> {
    List<LegacyAdGroup> findAllByCampaignIdAndDeletedAtIsNull(Long id);
}
