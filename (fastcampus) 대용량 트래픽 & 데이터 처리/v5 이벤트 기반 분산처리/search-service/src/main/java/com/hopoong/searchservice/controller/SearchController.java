package com.hopoong.searchservice.controller;

import com.hopoong.searchservice.model.ProductTagsDto;
import com.hopoong.searchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;


    /*
     * 태그 등록
     */
    @PostMapping("/search/addTagCache")
    public void addTagCache(@RequestBody ProductTagsDto productTagsDto) {
        searchService.addTagCache(productTagsDto.getProductId(), productTagsDto.getTags());
    }


    /*
     * 태그 삭제
     */
    @PostMapping("/search/removeTagCache")
    public void removeTagCache(@RequestBody ProductTagsDto productTagsDto) {
        searchService.removeTagCache(productTagsDto.getProductId(), productTagsDto.getTags());
    }


    /*
     * 태그 검색
     */
    @GetMapping("/search/tags/{tag}/productIds")
    public List<Long> removeTagCache(@PathVariable String tag) {
        return searchService.getProductIdByTag(tag);
    }


}
