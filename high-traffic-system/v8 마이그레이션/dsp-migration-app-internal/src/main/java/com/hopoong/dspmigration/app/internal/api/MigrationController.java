package com.hopoong.dspmigration.app.internal.api;


import com.hopoong.dspmigration.app.core.dispatcher.MigrationDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration/v1")
public class MigrationController {

    private final MigrationDispatcher migrationDispatcher;

    @PutMapping("/retry")
    public MigrationRetryResp retryMigration(@RequestBody MigrationRetryRequest request) {
        boolean result = migrationDispatcher.dispatch(request.userId(), request.aggregateId(),
                request.aggregateType());
        return new MigrationRetryResp(result);
    }
}
