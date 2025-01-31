package com.hopoong.dspmigration.app.core.repository.legacy.campaign;

import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyCampaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyCampaignRepository  extends CrudRepository<LegacyCampaign, Long> {

}