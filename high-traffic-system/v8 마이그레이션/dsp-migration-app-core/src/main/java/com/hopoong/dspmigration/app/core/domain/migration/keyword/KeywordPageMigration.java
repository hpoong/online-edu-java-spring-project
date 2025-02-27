package com.hopoong.dspmigration.app.core.domain.migration.keyword;


import com.hopoong.dspmigration.app.core.domain.AggregateType;
import com.hopoong.dspmigration.app.core.domain.migration.PageMigration;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class KeywordPageMigration extends PageMigration<KeywordPageMigration> {
    public KeywordPageMigration(Long id, Integer pageNumber, Integer pageSize, Long totalCount) {
        super(id, pageNumber, pageSize, totalCount);
    }
    public static KeywordPageMigration first(boolean isSuccess, Long id, Integer pageSize,
                                             Long totalCount) {
        if (isSuccess) {
            return new KeywordPageMigration(id, INIT_PAGE_NUMBER, pageSize, totalCount);
        }
        return new KeywordPageMigration(id, NOT_STARTED_PAGE_NUMBER, pageSize, totalCount);
    }
    @Override
    protected AggregateType aggregateType() {
        return AggregateType.KEYWORD;
    }
}
