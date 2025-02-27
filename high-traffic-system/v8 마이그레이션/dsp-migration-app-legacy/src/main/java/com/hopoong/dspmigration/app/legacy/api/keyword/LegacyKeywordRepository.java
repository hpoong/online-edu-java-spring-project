package com.hopoong.dspmigration.app.legacy.api.keyword;

import com.hopoong.dspmigration.app.legacy.domain.LegacyKeyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegacyKeywordRepository extends CrudRepository<LegacyKeyword, Long> {

}