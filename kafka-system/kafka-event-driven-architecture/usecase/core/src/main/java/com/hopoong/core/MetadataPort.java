package com.hopoong.core;

import java.util.List;

public interface MetadataPort {

    String getCategoryNameByCategoryId(Long categoryId);
    String getUserNameByUserId(Long userId);
    List<Long> listFollowerIdsByUserId(Long userId);
}
