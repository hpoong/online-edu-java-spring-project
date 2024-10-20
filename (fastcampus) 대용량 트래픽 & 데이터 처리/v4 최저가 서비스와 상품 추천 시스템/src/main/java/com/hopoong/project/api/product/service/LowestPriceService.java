package com.hopoong.project.api.product.service;

import com.hopoong.project.api.product.vo.Keyword;
import com.hopoong.project.api.product.vo.Product;
import com.hopoong.project.api.product.vo.ProductGrp;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setNewProduct(Product newProduct);

    int setNewProductGrp(ProductGrp newProductGrp);

    int setNewProductGrpToKeyword(String keyword, String prodGrpId, double score);

    Keyword getLowestPriceProductByKeyword(String keyword);
}
