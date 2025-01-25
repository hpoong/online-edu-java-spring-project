package com.hopoong.dspmigration.app.legacy.api.campaign;

import com.hopoong.dspmigration.app.legacy.domain.LegacyCampaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyCampaignRepository  extends CrudRepository<LegacyCampaign, Long> {

}