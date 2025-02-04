package com.hopoong.dspmigration.app.core.app.pagemigration;

import com.hopoong.dspmigration.app.core.converter.LegacyMigrationService;
import com.hopoong.dspmigration.app.core.domain.legacyad.DeletableEntity;
import com.hopoong.dspmigration.app.core.domain.migration.PageMigration;
import com.hopoong.dspmigration.app.core.domain.recent.MigratedEntity;
import com.hopoong.dspmigration.app.core.repository.legacy.LegacyPageableRepository;
import com.hopoong.dspmigration.app.core.repository.migration.PageMigrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class PageLegacyMigrationService<P extends PageMigration<P>, Legacy extends DeletableEntity, Recent extends MigratedEntity> {


    public final static Integer PAGE_SIZE = 1;

    protected final PageMigrationRepository<P> pageMigrationRepository;
    protected final LegacyPageableRepository<Legacy> legacyPageableRepository;
    protected final LegacyMigrationService<Legacy, Recent> legacyMigrationService;

    @Transactional
    public PageMigrationResult migrate(Long userId) {
        return pageMigrationRepository.findById(userId)
                .map(this::migrateNextPage)
                .orElseGet(() -> startPageMigration(userId));
    }

    private PageMigrationResult startPageMigration(Long userId) {
        Integer pageNumber = PageMigration.INIT_PAGE_NUMBER;
        Page<Legacy> legacyPage = findPage(userId, pageNumber);
        boolean isSuccess = migrate(legacyPage);
        P pageMigration = firstPageMigration(userId, isSuccess, legacyPage);
        pageMigrationRepository.save(pageMigration);
        return new PageMigrationResult(userId, pageMigration.getPageNumber(),
                legacyPage.getTotalPages(),
                pageMigration.getTotalCount(), isSuccess);
    }
    protected abstract P firstPageMigration(Long userId, boolean isSuccess, Page<Legacy> legacyPage);

    private Page<Legacy> findPage(Long userId, Integer pageNumber) {
        return legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(userId,
                PageRequest.of(pageNumber, PAGE_SIZE));
    }

    private boolean migrate(Page<Legacy> legacyPage) {
        return legacyMigrationService.migrate(legacyPage.toList());
    }


    private PageMigrationResult migrateNextPage(P pageMigration) {
        Integer pageNumber = pageMigration.nextPageNumber();
        Page<Legacy> legacyPage = findPage(pageMigration.getId(), pageNumber);
        boolean isSuccess = migrate(legacyPage);
        pageMigration.progress(isSuccess, legacyPage.getTotalElements());
        pageMigrationRepository.save(pageMigration);
        return new PageMigrationResult(pageMigration.getId(), pageMigration.getPageNumber(),
                legacyPage.getTotalPages(), pageMigration.getTotalCount(), isSuccess);
    }

}
