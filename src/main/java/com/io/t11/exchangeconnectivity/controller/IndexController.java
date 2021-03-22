package com.io.t11.exchangeconnectivity.controller;

import com.io.t11.exchangeconnectivity.dto.Index;
import com.io.t11.exchangeconnectivity.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")

public class IndexController {
    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService){
        this.indexService = indexService;
    }

    @GetMapping
    public Index index() {
        return indexService.index();
    }
}
