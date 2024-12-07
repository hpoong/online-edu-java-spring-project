package com.hopoong.catalogservice.controller;

import com.hopoong.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

//    @PostMapping("catalog/products/{productId}")
//
//    @DeleteMapping("/catalog/products/{productId}")
//
//    @GetMapping("/catalog/products/{productId}")
//
//    @GetMapping("/catalog/sellers/{sellerId}/products")
//
//    @PostMapping("/catalog/products/{productId}/decresseStockCount")

}
