package com.io.t11.exchangeconnectivity.controller;

import com.io.t11.exchangeconnectivity.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ExchangeController {
    @Autowired
    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }
}
