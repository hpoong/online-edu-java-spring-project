package com.hopoong.dspmigration.app.modern.repository.legacy.keyword;

import com.hopoong.dspmigration.app.modern.domain.legacyad.LegacyKeyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyKeywordRepository extends CrudRepository<LegacyKeyword, Long> {

}