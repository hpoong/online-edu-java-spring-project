package com.hopoong.featureflag_app.controller;

import com.hopoong.featureflag_usecase.service.FeatureflagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resolve")
public class FeatureflagResolveController {

    private final FeatureflagService featureflagService;


    @GetMapping("/string/{key}")
    public String getStringFlagValue(@PathVariable String key) {
        String targetKey = key.isEmpty() ? "myStrtingFlag" : key;
        return featureflagService.getStringFlag(targetKey);
    }
}
