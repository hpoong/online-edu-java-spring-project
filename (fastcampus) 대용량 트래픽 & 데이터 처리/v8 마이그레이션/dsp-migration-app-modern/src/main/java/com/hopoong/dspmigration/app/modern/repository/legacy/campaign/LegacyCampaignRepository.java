package com.hopoong.dspmigration.app.modern.repository.legacy.campaign;

import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyCampaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyCampaignRepository  extends CrudRepository<LegacyCampaign, Long> {

}