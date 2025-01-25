package com.hopoong.dspmigration.app.modern.repository.recent.user;

import com.hopoong.dspmigration.app.modern.domain.recent.RecentUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecentUserRepository extends CrudRepository<RecentUser, Long> {

}