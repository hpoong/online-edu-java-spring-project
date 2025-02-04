package com.hopoong.dspmigration.app.core.repository.migration;

import com.hopoong.dspmigration.app.core.domain.migration.PageMigration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface PageMigrationRepository<T extends PageMigration<T>> extends CrudRepository<T, Long> {

}