package com.hopoong.dspmigration.app.core.app.keyword;


import com.hopoong.dspmigration.app.core.converter.LegacyConverter;
import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyKeyword;
import com.hopoong.dspmigration.app.core.domain.recent.RecentKeyword;
import org.springframework.stereotype.Component;

@Component
public class LegacyKeywordConverter  implements LegacyConverter<LegacyKeyword, RecentKeyword> {

    public RecentKeyword convert(LegacyKeyword legacyKeyword) {
        return RecentKeyword.migrated(legacyKeyword.getId(), legacyKeyword.getText(),
                legacyKeyword.getAdGroupId(), legacyKeyword.getUserId(), legacyKeyword.getCreatedAt(),
                legacyKeyword.getDeletedAt());
    }
}
