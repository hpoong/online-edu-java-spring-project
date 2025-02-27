package com.hopoong.dspmigration.app.legacy.api.user;

import com.hopoong.dspmigration.app.legacy.api.user.model.LegacyUserCreateCommand;
import com.hopoong.dspmigration.app.legacy.api.user.model.LegacyUserResult;
import com.hopoong.dspmigration.app.legacy.domain.LegacyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LegacyUserService {

    private final LegacyUserRepository repository;

    @Transactional
    public LegacyUserResult create(
            LegacyUserCreateCommand command) {
        LegacyUser newUser = LegacyUser.of(command.name());
        return LegacyUserResult.from(repository.save(newUser));
    }

    public LegacyUserResult find(Long id) {
        return LegacyUserResult.from(findById(id));
    }

    private LegacyUser findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public LegacyUserResult updateName(Long id, String name) {
        LegacyUser user = findById(id);
        user.updateName(name);
        return LegacyUserResult.from(repository.save(user));
    }

    @Transactional
    public LegacyUserResult delete(Long id) {
        LegacyUser user = findById(id);
        user.delete();
        return LegacyUserResult.from(repository.save(user));
    }
}