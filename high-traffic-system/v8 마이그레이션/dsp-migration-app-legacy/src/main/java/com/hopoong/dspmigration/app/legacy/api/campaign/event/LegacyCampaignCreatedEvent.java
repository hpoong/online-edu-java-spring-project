package com.hopoong.dspmigration.app.legacy.api.campaign.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyCampaign;

import java.time.LocalDateTime;

public class LegacyCampaignCreatedEvent  extends LegacyCampaignEvent {

    public LegacyCampaignCreatedEvent(LegacyCampaign legacyCampaign) {
        super(legacyCampaign);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyCampaign.getCreatedAt();
    }

}