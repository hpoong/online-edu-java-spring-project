package com.hopoong.dspmigration.app.modern.app.keyword;


import com.hopoong.dspmigration.app.modern.app.legacyad.LegacyConverter;
import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyKeyword;
import com.hopoong.dspmigration.app.modern.domain.recent.RecentKeyword;
import org.springframework.stereotype.Component;

@Component
public class LegacyKeywordConverter  implements LegacyConverter<LegacyKeyword, RecentKeyword> {

    public RecentKeyword convert(LegacyKeyword legacyKeyword) {
        return RecentKeyword.migrated(legacyKeyword.getId(), legacyKeyword.getText(),
                legacyKeyword.getAdGroupId(), legacyKeyword.getUserId(), legacyKeyword.getCreatedAt(),
                legacyKeyword.getDeletedAt());
    }
}
