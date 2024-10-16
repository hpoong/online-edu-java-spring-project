package com.hopoong.project.api.product.controller;

import com.hopoong.project.api.product.service.LowestPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class LowestPriceController {


    private final LowestPriceService lowestPriceService;


    @Tag(name = "Lowest Price API")
    @Operation(summary = "TEST API", description = "TEST API 호출")
    @GetMapping("/get-zset-value")
    public Set getZsetValue(String key) {
        return lowestPriceService.getZsetValue(key);
    }


}
