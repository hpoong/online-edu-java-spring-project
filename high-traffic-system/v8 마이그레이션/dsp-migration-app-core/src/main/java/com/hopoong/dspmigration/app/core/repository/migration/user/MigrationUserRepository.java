package com.hopoong.dspmigration.app.core.repository.migration.user;

import com.hopoong.dspmigration.app.core.domain.migration.user.MigrationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationUserRepository extends CrudRepository<MigrationUser, Long> {
}
