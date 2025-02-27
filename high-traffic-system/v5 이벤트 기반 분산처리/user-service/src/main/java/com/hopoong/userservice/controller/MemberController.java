package com.hopoong.userservice.controller;

import com.hopoong.userservice.entity.UserEntity;
import com.hopoong.userservice.model.ModifyUserDto;
import com.hopoong.userservice.model.RegisterUserDto;
import com.hopoong.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UserService userService;

    @PostMapping("/member/users/register")
    public UserEntity registerUser(@RequestBody RegisterUserDto dto) {
        return userService.registerUser(dto.getLoginId(), dto.getUserName());
    }


    @PutMapping("/member/users/{userId}/modify")
    public UserEntity registerUser(@PathVariable("userId") String userId,  @RequestBody ModifyUserDto dto) {
        return userService.modifyUser(userId, dto.getUserName());
    }


    @GetMapping("/member/users/{userId}/login")
    public UserEntity login(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

}
