package com.hopoong.catalogservice.service;

import blackfriday.protobuf.EdaMessage;
import com.hopoong.catalogservice.cassandra.entity.ProductEntity;
import com.hopoong.catalogservice.cassandra.repository.ProductRepository;
import com.hopoong.catalogservice.feign.SearchClient;
import com.hopoong.catalogservice.model.ProductTagsDto;
import com.hopoong.catalogservice.mysql.entity.SellerProductEntity;
import com.hopoong.catalogservice.mysql.repository.SellerProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ProductRepository productRepository;
    private final SellerProductRepository sellerProductRepository;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public ProductEntity registerProduct(
        Long sellerId,
        String name,
        String description,
        Long price,
        Long stockCount,
        List<String> tags
    ) {
        SellerProductEntity sellerProductEntity = sellerProductRepository.save(new SellerProductEntity(sellerId));
        sellerProductRepository.save(sellerProductEntity);

        ProductEntity productEntity = new ProductEntity(
                sellerProductEntity.getId(),
                sellerId,
                name,
                description,
                price,
                stockCount,
                tags);


        // TODO : 제품이 등록 되면 redis에 tag 등록
//        var dto = new ProductTagsDto();
//        dto.setTags(tags);
//        dto.setProductId(productEntity.getId());
//        searchClinet.addTagCache(dto);


        // TODO : 변경
        var message = EdaMessage.ProductTags.newBuilder()
                .setProductId(productEntity.getId())
                .addAllTags(tags)
                .build();

        kafkaTemplate.send("product_tags_added", message.toByteArray());

        return productRepository.save(productEntity);
    }


    public void deleteProduct(Long productId) {


        var product = productRepository.findById(productId);

        if(product.isPresent()) {

            // TODO : 제품이 등록 되면 redis에 tag 삭제
//            var dto = new ProductTagsDto();
//            dto.setTags(product.get().getTags());
//            dto.setProductId(product.get().getId());
//            searchClinet.removeTagCache(dto);


            // TODO : 변경
            var message = EdaMessage.ProductTags.newBuilder()
                    .setProductId(product.get().getId())
                    .addAllTags(product.get().getTags())
                    .build();

            kafkaTemplate.send("product_tags_remove", message.toByteArray());
        }



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
