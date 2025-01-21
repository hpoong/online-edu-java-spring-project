package com.hopoong.dspmigration.app.legacy.api.campaign;

import com.hopoong.dspmigration.app.legacy.api.campaign.model.LegacyCampaignCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.campaign.model.LegacyCampaignResult;
import com.hopoong.dspmigration.app.legacy.domain.LegacyCampaign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LegacyCampaignService {

    private final LegacyCampaignRepository repository;

    @Transactional
    public LegacyCampaignResult create(LegacyCampaignCreateCommand command) {
        LegacyCampaign newCampaign = LegacyCampaign.of(command.name(), command.userId(),
                command.budget());
        return LegacyCampaignResult.from(repository.save(newCampaign));
    }

    public LegacyCampaignResult find(Long id) {
        return LegacyCampaignResult.from(findById(id));

    }

    private LegacyCampaign findById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public LegacyCampaignResult updateName(Long id, String name) {
        LegacyCampaign campaign = findById(id);
        campaign.updateName(name);
        return LegacyCampaignResult.from(repository.save(campaign));
    }

    @Transactional
    public LegacyCampaignResult updateBudget(Long id, Long budget) {
        LegacyCampaign campaign = findById(id);
        campaign.updateBudget(budget);
        return LegacyCampaignResult.from(repository.save(campaign));
    }

    @Transactional
    public LegacyCampaignResult delete(Long id) {
        LegacyCampaign campaign = findById(id);
        campaign.delete();
        return LegacyCampaignResult.from(repository.save(campaign));
    }
}