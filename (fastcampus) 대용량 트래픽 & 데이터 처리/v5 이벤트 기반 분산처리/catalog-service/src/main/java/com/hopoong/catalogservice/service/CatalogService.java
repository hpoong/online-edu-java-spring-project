package com.hopoong.catalogservice.service;

import com.hopoong.catalogservice.cassandra.entity.ProductEntity;
import com.hopoong.catalogservice.cassandra.repository.ProductRepository;
import com.hopoong.catalogservice.mysql.entity.SellerProductEntity;
import com.hopoong.catalogservice.mysql.repository.SellerProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ProductRepository productRepository;
    private final SellerProductRepository sellerProductRepository;


    public ProductEntity registerProduct(
        Long sellerId,
        String name,
        String description,
        Long price,
        Long stockCount,
        List<String> tags
    ) {
        SellerProductEntity sellerProductEntity = sellerProductRepository.save(new SellerProductEntity(sellerId));
        ProductEntity productEntity = new ProductEntity(
                sellerProductEntity.getId(),
                sellerId,
                name,
                description,
                price,
                stockCount,
                tags);

        return productRepository.save(productEntity);
    }


    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        sellerProductRepository.deleteById(productId);
    }

    public List<ProductEntity> getProductsBySellerId(Long sellerId) {
        var sellerProducts = sellerProductRepository.findBySellerId(sellerId);

        var products = new ArrayList<ProductEntity>();

        for(var item : sellerProducts) {
            var product = productRepository.findById(item.getId());
            product.ifPresent(products::add);
        }
        return products;
    }


    public ProductEntity getProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }


    public  ProductEntity decreaseStockCount(Long productId, Long decreaseCount) {
        var product = productRepository.findById(productId).orElseThrow();
        product.setStockCount(product.getStockCount() - decreaseCount);
        return productRepository.save(product);
    }

}
