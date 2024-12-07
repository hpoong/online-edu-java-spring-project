package com.hopoong.catalogservice.service;

import com.hopoong.catalogservice.cassandra.entity.ProductEntity;
import com.hopoong.catalogservice.cassandra.repository.ProductRepository;
import com.hopoong.catalogservice.mysql.entity.SellerProductEntity;
import com.hopoong.catalogservice.mysql.repository.SellerProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.stereotype.Service;

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

        productRepository.save(productEntity);
        return null;
    }

}
