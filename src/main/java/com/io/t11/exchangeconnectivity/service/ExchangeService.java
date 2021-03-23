package com.io.t11.exchangeconnectivity.service;

import com.io.t11.exchangeconnectivity.model.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

    RestTemplate restTemplate = new RestTemplate();

    private String exchangeUrl="http://localhost:5050/api/trades/try";

    public String callMallon(OrderDto orderDto) {
        HttpEntity<OrderDto> entity = new HttpEntity<>(new OrderDto());
        return restTemplate.postForObject(exchangeUrl, entity, String.class);
    }
}
