package com.hopoong.featureflag_usecase.port;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

public interface GetStringValuePort {

    String getStringValue(GetStringValuePortRequest request);

    @Data
    @AllArgsConstructor
    class GetStringValuePortRequest {
        private String key;
    }
}
