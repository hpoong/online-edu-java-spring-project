package com.hopoong.paymentservice.controller;

import com.hopoong.paymentservice.entity.PaymentEntity;
import com.hopoong.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


//    @PostMapping("/member/users/register")
//    public PaymentEntity registerUser(@RequestBody RegisterUserDto dto) {
//        return paymentService.registerUser(dto.getLoginId(), dto.getUserName());
//    }
//
//
//    @PutMapping("/member/users/{userId}/modify")
//    public PaymentEntity registerUser(@PathVariable("userId") String userId, @RequestBody ModifyUserDto dto) {
//        return paymentService.modifyUser(userId, dto.getUserName());
//    }
//
//
//    @GetMapping("/member/users/{userId}/login")
//    public PaymentEntity login(@PathVariable("userId") String userId) {
//        return paymentService.getUser(userId);
//    }



}
