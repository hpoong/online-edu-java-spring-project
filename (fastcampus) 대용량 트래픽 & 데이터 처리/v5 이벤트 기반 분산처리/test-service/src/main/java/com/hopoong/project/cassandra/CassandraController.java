package com.hopoong.project.cassandra;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CassandraController {

    private final CassandraService cassandraService;


    @GetMapping("/test1")
    public String test() {
        cassandraService.casTest();
        return "good";
    }

}
