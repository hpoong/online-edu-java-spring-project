package com.hopoong.dspmigration.app.core.repository.recent.campaign;

import com.hopoong.dspmigration.app.core.domain.recent.RecentCampaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentCampaignRepository extends CrudRepository<RecentCampaign, Long> {

}