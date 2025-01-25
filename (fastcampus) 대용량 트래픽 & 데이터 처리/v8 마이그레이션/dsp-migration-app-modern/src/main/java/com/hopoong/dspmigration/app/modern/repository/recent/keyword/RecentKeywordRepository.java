package com.hopoong.dspmigration.app.modern.repository.recent.keyword;

import com.hopoong.dspmigration.app.modern.domain.recent.RecentKeyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentKeywordRepository extends CrudRepository<RecentKeyword, Long> {

}