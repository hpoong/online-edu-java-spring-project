package com.hopoong.catalogservice.controller;

import com.hopoong.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

}
