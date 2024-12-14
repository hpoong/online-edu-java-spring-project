package com.hopoong.catalogservice.feign;

import com.hopoong.catalogservice.model.ProductTagsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "searchClient", url = "http://localhost:8080")
public interface SearchClient {

    // TODO : 이벤트 기반 아키텍처로 변경으로 인한 내용으로 필요가 없어짐.

    @PostMapping("/search/addTagCache")
    void addTagCache(@RequestBody ProductTagsDto productTagsDto);


    @PostMapping("/search/removeTagCache")
    void removeTagCache(@RequestBody ProductTagsDto productTagsDto);
}
