package com.hopoong.couponapi.controller;


import com.hopoong.couponapi.controller.dto.CouponIssueRequestDto;
import com.hopoong.couponapi.controller.dto.CouponIssueResponseDto;
import com.hopoong.couponapi.service.CouponIssueRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class CouponIssueController {

    private final CouponIssueRequestService couponIssueRequestService;


    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issuev1(@RequestBody CouponIssueRequestDto couponIssueRequestDto) {
        couponIssueRequestService.issueRequestV1(couponIssueRequestDto);
        return new CouponIssueResponseDto(true, null);
    }

}
