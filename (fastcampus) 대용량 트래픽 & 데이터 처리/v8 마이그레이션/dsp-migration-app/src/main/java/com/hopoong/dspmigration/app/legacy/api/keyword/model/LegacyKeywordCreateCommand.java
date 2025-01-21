package com.hopoong.dspmigration.app.legacy.api.keyword.model;

public record LegacyKeywordCreateCommand(String text, Long adGroupId, Long userId) {

}