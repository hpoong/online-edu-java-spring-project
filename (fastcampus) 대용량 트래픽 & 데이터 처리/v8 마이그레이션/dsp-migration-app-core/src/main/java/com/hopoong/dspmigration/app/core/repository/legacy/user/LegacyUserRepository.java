package com.hopoong.dspmigration.app.core.repository.legacy.user;

import com.hopoong.dspmigration.app.core.domain.legacyad.LegacyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LegacyUserRepository extends CrudRepository<LegacyUser, Long> {

}