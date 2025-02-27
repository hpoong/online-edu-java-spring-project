package com.hopoong.project.api.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "상품 정보를 나타내는 모델")
public class Product {

    @Schema(description = "상품 그룹 ID", example = "prodGrpId_001")
    private String prodGrpId;

    @Schema(description = "상품 고유 ID", example = "productId_001")
    private String productId;

    @Schema(description = "상품 가격", example = "25000")
    private int price;
}
