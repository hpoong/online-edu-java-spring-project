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

    @PostMapping("catalog/products/{productId}")
    public ProductEntity registerProduct(@RequestBody RegisterProductDto dto) {
        return catalogService.registerProduct(dto.getSellerId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStockCount(), dto.getTags());
    }

    @DeleteMapping("/catalog/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        catalogService.deleteProduct(productId);
    }

    @GetMapping("/catalog/products/{productId}")
    public ProductEntity getProductById(@PathVariable Long productId) {
        return catalogService.getProductId(productId);
    }

    @GetMapping("/catalog/sellers/{sellerId}/products")
    public List<ProductEntity> getProductsBySellerId(@PathVariable Long sellerId) {
        return catalogService.getProductsBySellerId(sellerId);
    }

    @PostMapping("/catalog/products/{productId}/decresseStockCount")
    public ProductEntity test(@PathVariable Long productId, @RequestBody DecreaseStockCountDto decreaseStockCountDto) {
        return catalogService.decreaseStockCount(productId, decreaseStockCountDto.getDecreaseCount());
    }

}
