package com.hopoong.catalogservice.feign;

import com.hopoong.catalogservice.model.DecreaseStockCountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "catalogClient", url = "http://localhost:8080")
public interface CatalogClient {


    /*
     * 제품 정보조회
     */
    @GetMapping("/catalog/products/{productId}")
    Map<String, Object> getProductById(@PathVariable Long productId);


    /*
     * 제품 재고 감소
     */
    @PostMapping("/catalog/products/{productId}/decresseStockCount")
    Map<String, Object> decreaseStockCount(@PathVariable Long productId, @RequestBody DecreaseStockCountDto decreaseStockCountDto);
}
