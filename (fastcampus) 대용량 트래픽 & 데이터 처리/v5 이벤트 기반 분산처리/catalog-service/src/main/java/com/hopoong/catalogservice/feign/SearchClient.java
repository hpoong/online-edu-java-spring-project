package com.hopoong.catalogservice.feign;

import com.hopoong.catalogservice.model.ProductTagsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "searchClient", url = "http://localhost:8080")
public interface SearchClient {

    @PostMapping("/search/addTagCache")
    void addTagCache(@RequestBody ProductTagsDto productTagsDto);


    @PostMapping("/search/removeTagCache")
    void removeTagCache(@RequestBody ProductTagsDto productTagsDto);
}
