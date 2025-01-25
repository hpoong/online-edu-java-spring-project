package com.hopoong.dspmigration.app.legacy.event;

import com.hopoong.dspmigration.app.legacy.api.adgroup.LegacyAdGroupService;
import com.hopoong.dspmigration.app.legacy.api.adgroup.event.*;
import com.hopoong.dspmigration.app.legacy.api.adgroup.model.LegacyAdGroupCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.adgroup.model.LegacyAdGroupResult;
import com.hopoong.dspmigration.app.legacy.api.campaign.LegacyCampaignService;
import com.hopoong.dspmigration.app.legacy.api.campaign.event.*;
import com.hopoong.dspmigration.app.legacy.api.campaign.model.LegacyCampaignCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.campaign.model.LegacyCampaignResult;
import com.hopoong.dspmigration.app.legacy.api.keyword.LegacyKeywordService;
import com.hopoong.dspmigration.app.legacy.api.keyword.event.LegacyKeywordCreatedEvent;
import com.hopoong.dspmigration.app.legacy.api.keyword.event.LegacyKeywordDeletedEvent;
import com.hopoong.dspmigration.app.legacy.api.keyword.event.LegacyKeywordEvent;
import com.hopoong.dspmigration.app.legacy.api.keyword.model.LegacyKeywordCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.keyword.model.LegacyKeywordResult;
import com.hopoong.dspmigration.app.legacy.api.user.LegacyUserService;
import com.hopoong.dspmigration.app.legacy.api.user.event.LegacyUserCreatedEvent;
import com.hopoong.dspmigration.app.legacy.api.user.event.LegacyUserDeletedEvent;
import com.hopoong.dspmigration.app.legacy.api.user.event.LegacyUserEvent;
import com.hopoong.dspmigration.app.legacy.api.user.event.LegacyUserNameUpdatedEvent;
import com.hopoong.dspmigration.app.legacy.api.user.model.LegacyUserCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.user.model.LegacyUserResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class LegacyDomainEventListenerTest {

    @Autowired
    LegacyKeywordService keywordService;

    @Autowired
    LegacyAdGroupService adGroupService;

    @Autowired
    LegacyCampaignService campaignService;

    @Autowired
    LegacyUserService userService;

    @MockBean
    LegacyDomainEventListener eventListener;

    @Test
    void userEvents() {
        LegacyUserCreateCommand command = new LegacyUserCreateCommand("사용자");

        LegacyUserResult result = userService.create(command);
        userService.updateName(result.id(), "사용자1");
        userService.delete(result.id());

        assertAll(
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserCreatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserNameUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserDeletedEvent.class)),
                () -> verify(eventListener, times(3)).handleEvent(any(LegacyUserEvent.class)),
                () -> verify(eventListener, times(3)).handleEvent(any(DomainEvent.class)));
    }


    @Test
    void campaignEvents() {
        LegacyCampaignCreateCommand command = new LegacyCampaignCreateCommand("캠페인", 1L, 200L);

        LegacyCampaignResult result = campaignService.create(command);
        campaignService.updateName(result.id(), "캠페인1");
        campaignService.updateBudget(result.id(), 1000L);
        campaignService.delete(result.id());

        assertAll(
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignCreatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(
                        any(LegacyCampaignNameUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(
                        any(LegacyCampaignBudgetUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignDeletedEvent.class)),
                () -> verify(eventListener, times(4)).handleEvent(any(LegacyCampaignEvent.class)),
                () -> verify(eventListener, times(4)).handleEvent(any(DomainEvent.class)));
    }

    @Test
    void adGroupEvents() {
        LegacyAdGroupCreateCommand command = new LegacyAdGroupCreateCommand("광고그룹", 1L, 1L,
                "https://www.fastcampus.com");

        LegacyAdGroupResult result = adGroupService.create(command);
        adGroupService.updateName(result.id(), "광고그룹1");
        adGroupService.updateLinkUrl(result.id(), "https://www.fastcampus1.com");
        adGroupService.delete(result.id());

        assertAll(
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupCreatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupNameUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(
                        any(LegacyAdGroupLinkUrlUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupDeletedEvent.class)),
                () -> verify(eventListener, times(4)).handleEvent(any(LegacyAdGroupEvent.class)),
                () -> verify(eventListener, times(4)).handleEvent(any(DomainEvent.class)));
    }

    @Test
    void keywordEvents() {
        LegacyKeywordCreateCommand command = new LegacyKeywordCreateCommand("keyword", 1L, 1L);

        LegacyKeywordResult result = keywordService.create(command);
        keywordService.delete(result.id());

        assertAll(
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyKeywordCreatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyKeywordDeletedEvent.class)),
                () -> verify(eventListener, times(2)).handleEvent(any(LegacyKeywordEvent.class)),
                () -> verify(eventListener, times(2)).handleEvent(any(DomainEvent.class)));
    }


}