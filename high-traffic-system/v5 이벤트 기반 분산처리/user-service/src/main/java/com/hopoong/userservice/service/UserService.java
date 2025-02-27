package com.hopoong.userservice.service;

import com.hopoong.userservice.entity.UserEntity;
import com.hopoong.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity registerUser(String userId, String userName) {
        var user = new UserEntity(userId, userName);
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity modifyUser(String userId, String userName) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userEntity.setUserName(userName);
        return userEntity;
    }

    public UserEntity getUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userEntity;
    }

}
