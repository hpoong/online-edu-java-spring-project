package com.hopoong.featureflag_adapter.openfeatureflag;

import com.hopoong.featureflag_usecase.port.GetStringValuePort;
import dev.openfeature.sdk.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class FlagdAdapter implements GetStringValuePort {

    private final Client flagdClient;


    // flagd SDK client instance
    @Override
    public String getStringValue(GetStringValuePortRequest request) {
        String result = flagdClient.getStringValue(request.getKey(), "null");

        if(result.equals("null")) {
            throw new RuntimeException("Flagd returned null for key: " + request.getKey());
        }

        return result;
    }
}
