package com.hopoong.dspmigration.app.legacy.api.campaign.model;

public record LegacyCampaignCreateCommand(String name, Long userId, Long budget) {

}