package com.hopoong.featureflag_usecase.service;

import com.hopoong.featureflag_usecase.port.GetStringValuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FeatureflagService {

    private final GetStringValuePort getStringValuePort;

    public String getStringFlag(String key) {
        return getStringValuePort.getStringValue(new GetStringValuePort.GetStringValuePortRequest(key));
    }
}
