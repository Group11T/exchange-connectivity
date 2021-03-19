package com.io.t11.exchangeconnectivity.service;

import com.io.t11.exchangeconnectivity.dto.Index;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IndexService {

    public Index index() {
        System.out.println("Hit index");
        return new Index(true, "Exchange Connectivity", LocalDate.now());
    }
}
