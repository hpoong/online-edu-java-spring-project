package com.hopoong.dspmigration.app.legacy.api.keyword;

import com.hopoong.dspmigration.app.legacy.api.keyword.model.LegacyKeywordCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.keyword.model.LegacyKeywordResult;
import com.hopoong.dspmigration.app.legacy.domain.LegacyKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LegacyKeywordService {

    private final LegacyKeywordRepository repository;

    @Transactional
    public LegacyKeywordResult create(
            LegacyKeywordCreateCommand command) {
        LegacyKeyword newKeyword = LegacyKeyword.of(command.text(), command.adGroupId(),
                command.userId());
        return LegacyKeywordResult.from(repository.save(newKeyword));
    }

    public LegacyKeywordResult find(Long id) {
        return LegacyKeywordResult.from(findById(id));
    }

    private LegacyKeyword findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public LegacyKeywordResult delete(Long id) {
        LegacyKeyword keyword = findById(id);
        keyword.delete();
        return LegacyKeywordResult.from(repository.save(keyword));
    }
}