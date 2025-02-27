package com.hopoong.dspmigration.app.core.repository.legacy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface LegacyPageableRepository<T> extends CrudRepository<T, Long> {
    Page<T> findAllByUserIdAndDeletedAtIsNullOrderById(Long userId, Pageable pageable);
}