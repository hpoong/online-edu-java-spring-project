package com.hopoong.connection_queue_management.api;

import com.hopoong.connection_queue_management.response.CommonResponseCodeEnum;
import com.hopoong.connection_queue_management.response.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public SuccessResponse test() {
        return new SuccessResponse(CommonResponseCodeEnum.C01, "tests");
    }
}
