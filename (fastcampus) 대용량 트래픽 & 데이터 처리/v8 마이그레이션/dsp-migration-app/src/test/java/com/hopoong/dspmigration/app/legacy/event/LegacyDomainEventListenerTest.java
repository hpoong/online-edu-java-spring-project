package com.hopoong.dspmigration.app.legacy.event;

import com.hopoong.dspmigration.app.legacy.api.user.LegacyUserService;
import com.hopoong.dspmigration.app.legacy.api.user.event.LegacyUserCreatedEvent;
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
    private LegacyUserService legacyUserService;

    @MockBean
    LegacyDomainEventListener legacyDomainEventListener;

    @Test
    void handleEvent() {
        LegacyUserResult result = legacyUserService.create(new LegacyUserCreateCommand("사용자1"));
        legacyUserService.updateName(result.id(), "사용자2");
        legacyUserService.delete(result.id());

        verify(legacyDomainEventListener, times(1)).handleEvent(any(LegacyUserCreatedEvent.class));
    }
}