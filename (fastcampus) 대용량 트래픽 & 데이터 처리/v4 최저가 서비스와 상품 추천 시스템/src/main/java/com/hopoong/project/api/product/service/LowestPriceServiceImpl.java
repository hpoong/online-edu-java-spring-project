package com.hopoong.project.api.product.service;

import com.hopoong.project.api.product.vo.Keyword;
import com.hopoong.project.api.product.vo.Product;
import com.hopoong.project.api.product.vo.ProductGrp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {


    private final RedisTemplate redisTemplate;

    @Override
    public Set getZsetValue(String key) {
        Set myTempSet = new HashSet();
        myTempSet = redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
        return myTempSet;
    }

    /*
     * 상품 기반 상품 ID
     */
    @Override
    public int setNewProduct(Product newProduct) {
        redisTemplate.opsForZSet().add(newProduct.getProdGrpId(), newProduct.getProductId(), newProduct.getPrice());
        return redisTemplate.opsForZSet().rank(newProduct.getProdGrpId(), newProduct.getProductId()).intValue();

//        for(int i=0; i<12; i++) {
//            String baseKey = "%s-%s".formatted(newProduct.getProdGrpId(), i);
//            redisTemplate.opsForZSet().add(baseKey, "%s-%s-%s".formatted(baseKey, newProduct.getProductId(), i), i+100);
//            for(int j=0; j<12; j++) {
//                redisTemplate.opsForZSet().add(baseKey, "%s-%s-%s".formatted(baseKey, newProduct.getProductId(), j), j+100);
//            }
//        }
//        return 0;
    }

    /*
     * 상품 그룹 ID 기반 상품 ID
     */
    @Override
    public int setNewProductGrp(ProductGrp newProductGrp) {

        List<Product> product = newProductGrp.getProductList();
        String productId = product.get(0).getProductId();
        double price = product.get(0).getPrice();
        redisTemplate.opsForZSet().add(newProductGrp.getProdGrpId(), productId, price);
        return redisTemplate.opsForZSet().zCard(newProductGrp.getProdGrpId()).intValue();
    }


    /*
     * 키워드 기반 상품 그룹 ID
     */
    @Override
    public int setNewProductGrpToKeyword (String keyword, String prodGrpId, double score){
        redisTemplate.opsForZSet().add(keyword, prodGrpId, score);
        return redisTemplate.opsForZSet().rank(keyword, prodGrpId).intValue();

//        for(int i=0; i<12; i++) {
//            redisTemplate.opsForZSet().add(keyword, "%s-%s".formatted(prodGrpId, i), i+100);
//        }
//        return 0;
    }


    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        Keyword returnInfo = new Keyword();
        returnInfo.setKeyword(keyword);
        returnInfo.setProductGrpList(getProdGrpUsingKeyword(keyword));
        return returnInfo;
    }


    public List<ProductGrp> getProdGrpUsingKeyword(String keyword) {

        /*
         * 1. keyword 조회
         * 2. keyword 기준으로 상품 그룹 평점 상위 10개 조회
         * 3. 상품 그룹 평점 상위 10개 loop 실행
         * 4. 상품 그룹의 상품 가격 하위 10개 조회
         */
        List<ProductGrp> returnInfo = new ArrayList<>();

        // keyword 를 통해 ProductGroup 가져오기 (상위 10개)
        List<String> prodGrpIdList = List.copyOf(redisTemplate.opsForZSet().reverseRange(keyword, 0, 9));

        // Loop 타면서 ProductGrpID 로 Product:price 가져오기 (하위 10개)
        for(String prodGrpId : prodGrpIdList) {

            List<Product> prodList = new ArrayList<>();
            ProductGrp tempProdGrp = new ProductGrp();

            // 기존 convertValue Map.class
            // ZSetOperations.TypedTuple 사용하여 value / score 처리 변경
            Set<ZSetOperations.TypedTuple<Object>> prodAndPriceList = redisTemplate.opsForZSet().rangeWithScores(prodGrpId, 0, 9);
            for (ZSetOperations.TypedTuple<Object> prodPriceObj : prodAndPriceList) {
                Product tempProduct = new Product();
                tempProduct.setProductId(prodPriceObj.getValue().toString()); // Product ID
                tempProduct.setPrice(prodPriceObj.getScore().intValue()); // Product 가격
                tempProduct.setProdGrpId(prodGrpId);
                prodList.add(tempProduct);
            }

            // 10개 product price 입력완료
            tempProdGrp.setProdGrpId(prodGrpId);
            tempProdGrp.setProductList(prodList);
            returnInfo.add(tempProdGrp);
        }

        return returnInfo;
    }



}
