package com.hopoong.dspmigration.app.legacy.repository;

import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LegacyUserRepository extends CrudRepository<LegacyUser, Long> {

}
