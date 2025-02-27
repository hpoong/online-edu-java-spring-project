package com.hopoong.dspmigration.app.core.repository.legacy.keyword;

import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyKeyword;
import com.hopoong.dspmigration.app.core.repository.legacy.LegacyPageableRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyKeywordRepository extends LegacyPageableRepository<LegacyKeyword> {

}