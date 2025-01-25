package com.hopoong.dspmigration.app.legacy.api.campaign.event;

import com.hopoong.dspmigration.app.legacy.domain.LegacyCampaign;
import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;

import java.time.LocalDateTime;

public class LegacyCampaignNameUpdatedEvent extends LegacyCampaignEvent {

    public LegacyCampaignNameUpdatedEvent(LegacyCampaign legacyCampaign) {
        super(legacyCampaign);
    }

    @Override
    public LocalDateTime occurredOn() {
        return legacyCampaign.getUpdatedAt();
    }

}