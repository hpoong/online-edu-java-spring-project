package com.hopoong.project.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);
}
