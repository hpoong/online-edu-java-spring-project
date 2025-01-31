package com.hopoong.dspmigration.app.internal.api;

import com.hopoong.dspmigration.app.core.app.migration_user.MigrationUserResult;
import com.hopoong.dspmigration.app.core.app.migration_user.MigrationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration/v1/user")
public class MigrationUserController {
    private final MigrationUserService migrationUserService;

    @PostMapping("/{userId}/agree")
    public MigrationUserResp agreeMigration(@PathVariable Long userId) {
        MigrationUserResult result = migrationUserService.agree(userId);
        return new MigrationUserResp(result.id(), result.status(),
                result.agreedDate(), result.updateDate());
    }

    @GetMapping("/{userId}")
    public MigrationUserResp findMigrationUser(@PathVariable Long userId) {
        MigrationUserResult result = migrationUserService.findById(userId);
        return new MigrationUserResp(result.id(), result.status(),
                result.agreedDate(), result.updateDate());
    }
}
