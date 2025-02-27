package com.hopoong.dspmigration.app.core.repository.recent.keyword;

import com.hopoong.dspmigration.app.core.domain.recent.RecentKeyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentKeywordRepository extends CrudRepository<RecentKeyword, Long> {

}