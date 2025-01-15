package com.hopoong.featureflag_adapter.openfeatureflag;

import com.hopoong.featureflag_usecase.port.GetStringValuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlagdAdapter implements GetStringValuePort {


    // flagd SDK client instance
    @Override
    public String getStringValue(GetStringValuePortRequest request) {
        // get feature from flagd engine
        return "defulatValue";
    }
}
