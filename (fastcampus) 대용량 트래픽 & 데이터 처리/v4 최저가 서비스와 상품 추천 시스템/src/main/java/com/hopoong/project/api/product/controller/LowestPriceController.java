package com.hopoong.project.api.product.controller;

import com.hopoong.project.api.product.service.LowestPriceService;
import com.hopoong.project.api.product.vo.Keyword;
import com.hopoong.project.api.product.vo.Product;
import com.hopoong.project.api.product.vo.ProductGrp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class LowestPriceController {


    private final LowestPriceService lowestPriceService;


    @Tag(name = "Lowest Price API")
    @Operation(summary = "TEST API", description = "TEST API 호출")
    @GetMapping("/get-zset-value")
    public Set getZsetValue(@RequestParam("key") String key) {
        return lowestPriceService.getZsetValue(key);
    }

    @Tag(name = "Lowest Price API")
    @Operation(summary = "제품등록", description = "제품 등록을 한다.")
    @PutMapping("/product")
    public int setNewProduct(@RequestBody Product newProduct) {
        return lowestPriceService.setNewProduct(newProduct);
//        http://localhost:19000/product
//        {
//            "prodGrpId": "golf-groupId",
//            "productId": "prodId",
//            "price": 0
//        }
    }

    @Tag(name = "Lowest Price API")
    @Operation(summary = "제품 그룹 등록", description = "제품 그룹을 한다.")
    @PutMapping("/productGroup")
    public int setNewProductGroup(@RequestBody ProductGrp newProductGrp) {
        return lowestPriceService.setNewProductGrp(newProductGrp);
    }

    @Tag(name = "Lowest Price API")
    @Operation(summary = "키워드 기반 상품 그룹 등록", description = "키워드 기반 상품 그룹 등록을 한다.")
    @PutMapping("/productGroupToKeyword")
    public int setNewProductGrpToKeyword (String keyword, String prodGrpId, double score) {
        return lowestPriceService.setNewProductGrpToKeyword(keyword, prodGrpId, score);
//        http://localhost:19000/productGroupToKeyword?keyword=golf&prodGrpId=golf-groupId&score=1000
    }


    @Tag(name = "Lowest Price API")
    @Operation(summary = "키워드 기반 최저가 상품 조회", description = "키워드 기반 최저가 상품 조회를 한다.")
    @GetMapping("/productPrice/lowest")
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return lowestPriceService.getLowestPriceProductByKeyword(keyword);
    }

}
