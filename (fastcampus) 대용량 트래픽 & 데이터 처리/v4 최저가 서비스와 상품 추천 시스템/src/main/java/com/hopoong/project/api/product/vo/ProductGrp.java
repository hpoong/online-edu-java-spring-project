package com.hopoong.project.api.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "상품 그룹")
public class ProductGrp {

    @Schema(description = "상품 그룹 ID", example = "0001", required = true)
    private String prodGrpId;

    @Schema(description = "상품 리스트", required = true)
    private List<Product> productList;
}
