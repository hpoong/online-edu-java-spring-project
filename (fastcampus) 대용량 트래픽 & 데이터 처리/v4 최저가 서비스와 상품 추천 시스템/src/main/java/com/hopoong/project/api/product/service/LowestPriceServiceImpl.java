package com.hopoong.project.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    @Override
    public Set getZsetValue(String key) {
        Set myTempSet = new HashSet();
        return myTempSet;
    }
}
