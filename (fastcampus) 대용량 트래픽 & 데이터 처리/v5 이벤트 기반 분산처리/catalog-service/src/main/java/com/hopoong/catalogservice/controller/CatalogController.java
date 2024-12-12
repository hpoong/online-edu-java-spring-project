package com.hopoong.catalogservice.controller;

import com.hopoong.catalogservice.cassandra.entity.ProductEntity;
import com.hopoong.catalogservice.model.DecreaseStockCountDto;
import com.hopoong.catalogservice.model.RegisterProductDto;
import com.hopoong.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    /*
     * 제품 등록
     */
    @PostMapping("/catalog/products/{productId}")
    public ProductEntity registerProduct(@RequestBody RegisterProductDto dto) {
        return catalogService.registerProduct(dto.getSellerId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStockCount(), dto.getTags());
    }

    /*
     * 제품 삭제
     */
    @DeleteMapping("/catalog/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        catalogService.deleteProduct(productId);
    }

    /*
     * 제품 정보조회
     */
    @GetMapping("/catalog/products/{productId}")
    public ProductEntity getProductById(@PathVariable Long productId) {
        return catalogService.getProductId(productId);
    }

    /*
     * 판매자가 판매하는 제품 목록
     */
    @GetMapping("/catalog/sellers/{sellerId}/products")
    public List<ProductEntity> getProductsBySellerId(@PathVariable Long sellerId) {
        return catalogService.getProductsBySellerId(sellerId);
    }

    /*
     * 제품 재고 감소
     */
    @PostMapping("/catalog/products/{productId}/decresseStockCount")
    public ProductEntity test(@PathVariable Long productId, @RequestBody DecreaseStockCountDto decreaseStockCountDto) {
        return catalogService.decreaseStockCount(productId, decreaseStockCountDto.getDecreaseCount());
    }

}
