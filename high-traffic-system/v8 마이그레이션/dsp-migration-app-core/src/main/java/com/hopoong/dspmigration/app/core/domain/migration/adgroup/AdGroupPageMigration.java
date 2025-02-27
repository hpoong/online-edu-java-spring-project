package com.hopoong.dspmigration.app.core.domain.migration.adgroup;

import com.hopoong.dspmigration.app.core.domain.AggregateType;
import com.hopoong.dspmigration.app.core.domain.migration.PageMigration;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class AdGroupPageMigration extends PageMigration<AdGroupPageMigration> {
    private AdGroupPageMigration(Long id, Integer pageNumber, Integer pageSize, Long totalCount) {
        super(id, pageNumber, pageSize, totalCount);
    }
    public static AdGroupPageMigration first(boolean isSuccess, Long id, Integer pageSize,
                                             Long totalCount) {
        if (isSuccess) {
            return new AdGroupPageMigration(id, INIT_PAGE_NUMBER, pageSize, totalCount);
        }
        return new AdGroupPageMigration(id, NOT_STARTED_PAGE_NUMBER, pageSize, totalCount);
    }
    @Override
    protected AggregateType aggregateType() {
        return AggregateType.ADGROUP;
    }
}