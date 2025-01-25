package com.hopoong.dspmigration.app.legacy.api.adgroup.model;

public  record LegacyAdGroupCreateCommand(String name, Long campaignId, Long userId,
                                          String linkUrl) {

}