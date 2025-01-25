package com.hopoong.dspmigration.app.modern.repository.recent.campaign;

import com.hopoong.dspmigration.app.modern.domain.recent.RecentCampaign;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentCampaignRepository extends CrudRepository<RecentCampaign, Long> {

}