package com.hopoong.dspmigration.app.legacy.api.campaign.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyCampaign;

import java.time.LocalDateTime;

public class LegacyCampaignDeletedEvent extends LegacyCampaignEvent {

    public LegacyCampaignDeletedEvent(LegacyCampaign legacyCampaign) {
        super(legacyCampaign);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyCampaign.getDeletedAt();
    }

}