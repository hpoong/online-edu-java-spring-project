package com.hopoong.dspmigration.app.core.repository.legacy.adgroup;

import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyAdGroup;
import com.hopoong.dspmigration.app.core.repository.legacy.LegacyPageableRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegacyAdGroupRepository extends LegacyPageableRepository<LegacyAdGroup> {
    List<LegacyAdGroup> findAllByCampaignIdAndDeletedAtIsNull(Long id);
    List<LegacyAdGroup> findAllByCampaignIdInAndDeletedAtIsNull(List<Long> campaignIds);
}
