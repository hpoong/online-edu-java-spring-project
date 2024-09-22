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


    /*
     * synchronized 활용 처리
     */
    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issuev1(@RequestBody CouponIssueRequestDto couponIssueRequestDto) {
        couponIssueRequestService.issueRequestV1(couponIssueRequestDto);
        return new CouponIssueResponseDto(true, null);
    }


    /*
     * redis lock 활용 처리
     */
    @PostMapping("/v2/issue")
    public CouponIssueResponseDto issuev2(@RequestBody CouponIssueRequestDto couponIssueRequestDto) {
        couponIssueRequestService.issueRequestV2(couponIssueRequestDto);
        return new CouponIssueResponseDto(true, null);
    }


    /*
     * db lock 활용 처리
     */
    @PostMapping("/v3/issue")
    public CouponIssueResponseDto issuev3(@RequestBody CouponIssueRequestDto couponIssueRequestDto) {
        couponIssueRequestService.issueRequestV3(couponIssueRequestDto);
        return new CouponIssueResponseDto(true, null);
    }

    /*
     * redis 를 통한 모든 처리 변경 - redis lock 활용
     */
    @PostMapping("/v1/issue-async")
    public CouponIssueResponseDto asyncIssuev1(@RequestBody CouponIssueRequestDto couponIssueRequestDto) {
        couponIssueRequestService.asyncIssueRequestV1(couponIssueRequestDto);
        return new CouponIssueResponseDto(true, null);
    }


    /*
     * redis 를 통한 모든 처리 변경 - redis script 활용
     */
    @PostMapping("/v2/issue-async")
    public CouponIssueResponseDto asyncIssuev2(@RequestBody CouponIssueRequestDto couponIssueRequestDto) {
        couponIssueRequestService.asyncIssueRequestV2(couponIssueRequestDto);
        return new CouponIssueResponseDto(true, null);
    }

}
