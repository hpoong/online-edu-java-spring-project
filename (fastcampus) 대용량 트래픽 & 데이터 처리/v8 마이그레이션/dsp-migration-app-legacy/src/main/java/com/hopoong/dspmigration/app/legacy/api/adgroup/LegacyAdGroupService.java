package com.hopoong.dspmigration.app.legacy.api.adgroup;

import com.hopoong.dspmigration.app.legacy.api.adgroup.model.LegacyAdGroupCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.adgroup.model.LegacyAdGroupResult;
import com.hopoong.dspmigration.app.legacy.domain.LegacyAdGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LegacyAdGroupService {

    private final LegacyAdGroupRepository repository;

    @Transactional
    public LegacyAdGroupResult create(LegacyAdGroupCreateCommand command) {
        LegacyAdGroup newAdGroup = LegacyAdGroup.of(command.name(), command.campaignId(),
                command.userId(), command.linkUrl());
        return LegacyAdGroupResult.from(repository.save(newAdGroup));
    }

    public LegacyAdGroupResult find(Long id) {
        return LegacyAdGroupResult.from(findById(id));
    }

    private LegacyAdGroup findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public LegacyAdGroupResult updateName(Long id, String name) {
        LegacyAdGroup adGroup = findById(id);
        adGroup.updateName(name);
        return LegacyAdGroupResult.from(repository.save(adGroup));
    }

    @Transactional
    public LegacyAdGroupResult updateLinkUrl(Long id, String linkUrl) {
        LegacyAdGroup adGroup = findById(id);
        adGroup.updateLinkUrl(linkUrl);
        return LegacyAdGroupResult.from(repository.save(adGroup));
    }

    @Transactional
    public LegacyAdGroupResult delete(Long id) {
        LegacyAdGroup adGroup = findById(id);
        adGroup.delete();
        return LegacyAdGroupResult.from(repository.save(adGroup));
    }
}